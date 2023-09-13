<template>
    <div class="list">
        <h3>주문 상세</h3>

        <fieldset v-for="(item, idx) in menuList">
            <!-- 수정 -->
            <legend>주문 번호: {{ item.order_product_number }}번</legend>
            <p>주문일자: {{ item.order_date }}</p>
            <p>주문상태: {{ item.order_state }}</p>
            <table style="border: 1px solid #000; text-align: center;">
                <thead>
                    <tr>
                        <th style="border: 1px solid #000;">NO</th>
                        <th style="border: 1px solid #000;">상품명</th>
                        <th style="border: 1px solid #000;">카테고리</th>
                        <th style="border: 1px solid #000;">온도</th>
                        <th style="border: 1px solid #000;">수량</th>
                        <th style="border: 1px solid #000;">주문금액</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, idx) in menuList">
                        <td style="border: 1px solid #000;">{{ idx }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_name }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_category }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_temperature }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_amount }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_price }}</td>
                    </tr>
                </tbody>
            </table>
            <button>수령 완료</button>
            <button>주문 취소</button>
            <!-- // 수정 -->
        </fieldset>

        <div class="container">
            <table style="border: 1px solid #000; text-align: center;">
                <thead>
                    <tr>
                        <th style="border: 1px solid #000;">NO</th>
                        <th style="border: 1px solid #000;">주문번호</th>
                        <th style="border: 1px solid #000;">상품명</th>
                        <th style="border: 1px solid #000;">주문금액</th>
                        <th style="border: 1px solid #000;">주문일</th>
                        <th style="border: 1px solid #000;">주문 상태</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, idx) in menuList">
                        <td style="border: 1px solid #000;">{{ idx }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_number }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_name }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_price }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_date }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_state }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    const App = {
        data: () => {
            return {
                testNumber: 1,
                testString: '문자열',

                emptyMenu: {
                    product_index: null,
                    product_name: null,
                    product_category: null,
                    product_price: null,
                    product_writer: null,
                    product_explan: null,
                    product_temperature: 'ice',
                    product_date: null
                },

                menu: {
                    product_name: null,
                    product_price: 0,
                    product_category: null,
                    product_explan: null,
                    product_temperature: null,
                    product_writer: null,
                    product_date: null,
                    searchType: "상품명",
                    searchKeyword: "",
                },

                menuList: [],

                selectedRow: null,

                isEditMode: false
            }
        },
        methods: {
            menuInsert: function () {
                let vm = this;
                vm.menu.product_date = new Date().toISOString().slice(0, 10);
                axios({
                    url: '/menuInsert.request',
                    method: 'post',
                    header: {
                        "Content-Type": "application/json; charset=UTF-8",
                    },
                    data: vm.menu,
                }).then(function (response) {
                    console.log('/menuInsert.request의 response 결과 값 : ' + response);
                    if (response.data > 0) {
                        alert("상품이 등록되었습니다.");
                        vm.menuSelectList();
                        vm.menu = vm.emptyMenu;
                    } else {
                        alert('등록된 메뉴가 없습니다.');
                    }
                }).catch(function (error) {
                    console.log('/menuInsert.request 에러 발생', error);
                    alert('메뉴 등록 에러가 발생하였습니다. 관리자에게 문의바랍니다.');
                })
            },

            menuSelectList: function () {
                let vm = this;
                let searchParams = {
                    searchType: vm.searchType,
                    searchKeyword: vm.searchKeyword,
                };

                axios({
                    url: '/menuSelectList.request',
                    method: 'get',
                    params: searchParams,
                }).then(function (response) {
                    vm.menuList = response.data;
                });
            },

            menuSearch: function () {
                this.menuSelectList();
            },

            menuSelectOne: function (item) {
                this.selectedRow = item.product_index;

                this.isEditMode = true;

                this.menu = {
                    product_index: item.product_index,
                    product_name: item.product_name,
                    product_category: item.product_category,
                    product_price: item.product_price,
                    product_writer: item.product_writer,
                    product_explan: item.product_explan,
                    product_temperature: item.product_temperature,
                    product_date: item.product_date
                };
            },

            resetForm: function () {
                this.menu = this.emptyMenu;
                this.isEditMode = false;
            },

            menuUpdate: function (item) {
                let vm = this;
                axios({
                    url: '/menuUpdate.request',
                    method: 'post',
                    headers: {
                        "Content-Type": "application/json; charset=UTF-8",
                    },
                    data: vm.menu,
                }).then(function (response) {
                    if (response.data > 0) {
                        vm.menuSelectList();
                        vm.resetForm();
                    }
                }).catch(function (error) {
                    console.log('/menuUpdate.request 에러 발생', error);
                    alert('메뉴 수정 에러가 발생하였습니다.');
                });
            },

            menuDelete: function (item) {
                console.log(item);
                let vm = this;
                axios({
                    url: '/menuDelete.request',
                    method: 'post',
                    data: { product_index: item },
                }).then(function (response) {
                    alert('메뉴 삭제가 완료되었습니다.');
                    console.log(response);
                    if (response.data > 0) {
                        vm.menuSelectList();
                    }
                }).catch(function (error) {
                    alert('메뉴 삭제 에러가 발생하였습니다.');
                });
            },

            handleButtonAction() {
                if (!this.menu.product_name) {
                    alert("상품명을 입력해 주세요.");
                    return;
                }

                if (!this.menu.product_category) {
                    alert("카테고리를 선택해 주세요.");
                    return;
                }

                if (!this.menu.product_price) {
                    alert("가격을 입력해 주세요.");
                    return;
                }

                if (!this.menu.product_writer) {
                    alert("작성자를 입력해 주세요.");
                    return;
                }

                if (this.isEditMode) {
                    this.menuUpdate();
                } else {
                    this.menuInsert();
                }
            },
            
        },
        computed: {
            buttonLabel() {
                return this.isEditMode ? '수정' : '등록';
            }
        },
        mounted() {
            console.log('메뉴관리화면이 마운트 됨');
            this.menuSelectList();
        },
    }

    export default App;
</script>