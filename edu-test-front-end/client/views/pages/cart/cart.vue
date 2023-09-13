<template>
    <div>
        <h1>상품 주문</h1>
        <table style="border: 1px solid #000; text-align: center;">
            <thead>
                <tr>
                    <th style="border: 1px solid #000;">NO</th>
                    <th style="border: 1px solid #000;">상품명</th>
                    <th style="border: 1px solid #000;">메뉴가격(단위:원)</th>
                    <th style="border: 1px solid #000;">담기</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, idx) in cartList">
                    <td style="border: 1px solid #000;">{{idx}}</td>
                    <td style="border: 1px solid #000;">{{item.product_name}}</td>
                    <td style="border: 1px solid #000;">{{item.product_price}}</td>
                    <td style="border: 1px solid #000;">
                        <button @click="cartInsert(item)">핫</button>
                        <button @click="cartInsert(item)">아이스</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <div>
            <h3>장바구니</h3>
            <button>삭제</button>

            <!-- 수정: 로컬스토리지에 저장된 아이템 표시 -->
            <table style="border: 1px solid #000; text-align: center;">
                <tbody>
                    <tr v-for="(item, idx) in cartList">
                        <td style="border: 1px solid #000;">
                            <input type="checkbox" />
                        </td>
                        <td style="border: 1px solid #000;">아메리카노</td>
                        <td style="border: 1px solid #000;">아메리카노</td>
                        <td style="border: 1px solid #000;">아이스</td>
                        <td style="border: 1px solid #000;">2</td>
                        <td style="border: 1px solid #000;">4000</td>
                    </tr>
                </tbody>
            </table>
            <button>주문하기</button>
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

                cartList: [],

                selectedRow: null,
            }
        },
        methods: {
            cartOrder: function () {
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

            cartInsert: function () {
                let vm = this;
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
                    vm.cartList = response.data;
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

            menuDelete: function (item) {
                console.log(item);
                let vm = this;
                localStorage.removeItem();
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
        watch: {
            "menu.product_category": function (newValue, oldValue) {
                if(this.categoryIce.includes(this.menu.product_category)) {
                    this.menu.product_temperature = "ice";
                }
            }
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