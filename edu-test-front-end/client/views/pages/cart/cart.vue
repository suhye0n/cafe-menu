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
                        <button @click="cartInsert(item, '핫')">핫</button>
                        <button @click="cartInsert(item, '아이스')">아이스</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <div>
            <h3>장바구니</h3>
            <button @click="removeSelectedItems()">삭제</button>

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
                                <button @click="updateAmount(item, 'increase')">+</button>
                                {{ item.order_amount }}
                                <button @click="updateAmount(item, 'decrease')">-</button>
                            </td>
                            <td style="border: 1px solid #000;">{{ item.product_price * item.order_amount }}</td>
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

    computed: {
        totalAmount() {
            return this.cartList.reduce((acc, item) => acc + (item.product_price * item.order_amount), 0);
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

        updateAmount(item, action) {
            const storedCart = JSON.parse(localStorage.getItem("cart")) || [];

            const updatedCart = storedCart.map(cartItem => {
                if (cartItem.product_name === item.product_name &&
                    cartItem.product_temperature === item.product_temperature) {
                    if (action === 'increase') {
                        cartItem.order_amount++;
                    } else if (action === 'decrease' && cartItem.order_amount > 1) {
                        cartItem.order_amount--;
                    }
                }
                return cartItem;
            });

            localStorage.setItem("cart", JSON.stringify(updatedCart));
            this.menuSelectList();
        },

        removeSelectedItems() {
            const checkboxes = document.querySelectorAll("input[type='checkbox']:checked");
            const storedCart = JSON.parse(localStorage.getItem("cart")) || [];

            checkboxes.forEach(checkbox => {
                const index = parseInt(checkbox.parentElement.parentElement.rowIndex - 1);
                storedCart.splice(index, 1);
            });

            localStorage.setItem("cart", JSON.stringify(storedCart));
            this.menuSelectList();
        }
    },

    mounted() {
        console.log('메뉴관리화면이 마운트 됨');
        this.menuSelectList();
    },
}

export default App;
</script>