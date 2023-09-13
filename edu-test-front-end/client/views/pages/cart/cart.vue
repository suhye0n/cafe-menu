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
                <tr v-for="(item, idx) in menuList">
                    <td style="border: 1px solid #000;">{{ idx }}</td>
                    <td style="border: 1px solid #000;">{{ item.product_name }}</td>
                    <td style="border: 1px solid #000;">{{ item.product_price }}</td>
                    <td style="border: 1px solid #000;">
                        <!-- 수정: 아이스 클릭하면 order_product_temperature 아이스로.. -->
                        <button @click="cartInsert(item, '핫')">핫</button>
                        <button @click="cartInsert(item, '아이스')">아이스</button>
                        <!-- // 수정 -->
                    </td>
                </tr>
            </tbody>
        </table>

        <div>
            <h3>장바구니</h3>
            <!-- 수정: 삭제 버튼 누르면 체크박스 체크된 아이템들 로컬스토리지에서 제거 및 장바구니에서 제거 -->
            <button @click="removeSelectedItems()">삭제</button>
            <!-- // 수정 -->

            <div v-if="cartList && cartList.length > 0">
                <table style="border: 1px solid #000; text-align: center;">
                    <thead>
                        <tr>
                            <th style="border: 1px solid #000;"></th>
                            <th style="border: 1px solid #000;">카테고리</th>
                            <th style="border: 1px solid #000;">상품명</th>
                            <th style="border: 1px solid #000;">온도</th>
                            <th style="border: 1px solid #000;">수량</th>
                            <th style="border: 1px solid #000;">가격</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, idx) in cartList" :key="idx">
                            <td style="border: 1px solid #000;"><input type="checkbox" /></td>
                            <td style="border: 1px solid #000;">{{ item.product_category }}</td>
                            <td style="border: 1px solid #000;">{{ item.product_name }}</td>
                            <td style="border: 1px solid #000;">{{ item.product_temperature }}</td>
                            <td style="border: 1px solid #000;">
                                <!-- 수정: +, - 버튼 누르면 order_amount 로컬스토리지 및 여기 화면에서 감소 -->
                                <button @click="updateAmount(item, 'increase')">+</button>
                                {{ item.order_amount }}
                                <button @click="updateAmount(item, 'decrease')">-</button>
                                <!-- // 수정 -->
                            </td>
                            <td style="border: 1px solid #000;">{{ item.product_price }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div v-else>
                <p>장바구니가 비어있습니다.</p>
            </div>
            <!-- 수정: 합계 -->
            <p>합계: {{ totalAmount }}원</p>
            <!-- // 수정 -->

            <button @click="cartOrder()">주문하기</button>
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

            menu: {
                product_name: null,
                product_price: 0,
                product_category: null,
                product_explan: null,
                product_temperature: null,
                product_writer: null,
                product_date: null,
                product_amount: 1,
            },

            cart: {
                order_product_number: 0,
                order_product_name: null,
                order_product_category: null,
                order_product_temperature: null,
                order_amount: 1,
                order_price: 0,
                order_date: null,
                order_state: "준비중",
            },

            menuList: [],

            cartList: [],

            selectedRow: null,
        }
    },
    methods: {
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

            const storedCart = JSON.parse(localStorage.getItem("cart")) || [];
            vm.cartList = storedCart;
        },

        cartInsert: function (item, temperature) {
            let newCartItem = {
                ...item,
                product_temperature: temperature,
                order_amount: 1
            };

            const storedCart = JSON.parse(localStorage.getItem("cart")) || [];
            storedCart.push(newCartItem);
            localStorage.setItem("cart", JSON.stringify(storedCart));
            this.menuSelectList();
        },

        cartOrder: function () {
            let vm = this;
            vm.menu.product_date = new Date().toISOString().slice(0, 10);
            axios({
                url: '/cartInsert.request',
                method: 'post',
                header: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.menu,
            }).then(function (response) {
                console.log('/cartInsert.request의 response 결과 값 : ' + response);
                if (response.data > 0) {
                    alert("주문이 완료되었습니다.");
                    vm.menuSelectList();
                    vm.menu = vm.emptyMenu;
                }
            }).catch(function (error) {
                console.log('/menuInsert.request 에러 발생', error);
                alert('주문 에러가 발생하였습니다. 관리자에게 문의바랍니다.');
            })
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
            if (this.categoryIce.includes(this.menu.product_category)) {
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