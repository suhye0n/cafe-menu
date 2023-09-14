<template>
    <div class="cart content">
        <h1>상품 주문</h1>
        <table>
            <thead>
                <tr>
                    <th>NO</th>
                    <th>상품명</th>
                    <th>메뉴가격(단위:원)</th>
                    <th>담기</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, idx) in menuList">
                    <td>{{ idx }}</td>
                    <td>{{ item.product_name }}</td>
                    <td>{{ item.product_price }}</td>
                    <td>
                        <button @click="cartInsert(item, '핫')" :disabled="!item.product_hot">핫</button>
                        <button @click="cartInsert(item, '아이스')" :disabled="!item.product_ice">아이스</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="container">
            <h3>장바구니</h3>

            <div v-if="cartList && cartList.length > 0">
            <button @click="removeSelectedItems()">삭제</button>
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" @change="toggleAll" v-model="allSelected" /></th>
                            <th>카테고리</th>
                            <th>상품명</th>
                            <th>온도</th>
                            <th>수량</th>
                            <th>가격</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, idx) in cartList" :key="idx">
                            <td><input type="checkbox" v-model="item.isSelected" /></td>
                            <td>{{ item.product_category }}</td>
                            <td>{{ item.product_name }}</td>
                            <td>{{ item.product_temperature }}</td>
                            <td>
                                <button @click="updateAmount(item, 'increase')">+</button>
                                {{ item.order_amount }}
                                <button @click="updateAmount(item, 'decrease')">-</button>
                            </td>
                            <td>{{ item.product_price * item.order_amount }}</td>
                        </tr>
                    </tbody>
                </table>
                <p>합계: {{ totalAmount }}원</p>
                <button class="confirm" @click="cartOrder()">주문하기</button>
            </div>
            <div v-else>
                <p>장바구니가 비어있습니다.</p>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

const App = {
    data: () => {
        return {
            orderProductNumber: 1,
            menuList: [],
            cartList: [],
            allSelected: false,

            menu: {
                product_name: null,
                product_price: 0,
                product_category: null,
                product_explan: null,
                product_ice: false,
                product_hot: false,
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
                order_state: "진행중",
            },
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

        toggleAll() {
            this.cartList.forEach((item) => {
                item.isSelected = this.allSelected;
            });
        },

        cartInsert: function (item, temperature) {
            const existingItem = this.cartList.find(cartItem =>
                cartItem.product_name === item.product_name &&
                cartItem.product_temperature === temperature
            );

            if (existingItem) {
                existingItem.order_amount++;
            } else {
                let newCartItem = {
                    ...item,
                    product_temperature: temperature,
                    order_amount: 1,
                    product_hot: !!item.product_hot,
                    product_ice: !!item.product_ice
                };
                this.cartList.push(newCartItem);
            }

            localStorage.setItem("cart", JSON.stringify(this.cartList));
        },

        menuInsert: function () {
            let vm = this;
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

        cartOrder: async function () {
            try {
                const vm = this;
                const storedCart = JSON.parse(localStorage.getItem("cart")) || [];

                if (storedCart.length === 0) {
                    alert("장바구니가 비어 있습니다.");
                    return;
                }

                const lastOrderNumberResponse = await axios.get('/getLastOrderNumber.request');
                const lastOrderNumber = lastOrderNumberResponse.data;
                const newOrderNumber = lastOrderNumber + 1;

                const createOrderPromises = storedCart.map(item => {
                    const orderData = {
                        order_product_number: newOrderNumber,
                        order_product_name: item.product_name,
                        order_product_category: item.product_category,
                        order_product_temperature: item.product_temperature,
                        order_amount: item.order_amount,
                        order_price: item.product_price * item.order_amount,
                        order_date: new Date().toISOString().slice(0, 10),
                        order_state: "진행중"
                    };

                    return axios.post('/orderInsert.request', orderData);
                });

                const createOrderResponses = await Promise.all(createOrderPromises);

                const isSuccess = createOrderResponses.every(response => response.status === 200);

                if (isSuccess) {
                    alert("주문이 완료되었습니다.");

                    vm.orderProductNumber++;
                    localStorage.removeItem("cart");
                    vm.menuSelectList();
                } else {
                    alert("주문 생성에 실패했습니다.");
                }
            } catch (error) {
                console.error('/orderInsert.request 에러 발생', error);
                alert('주문 생성 중 오류가 발생했습니다. 관리자에게 문의바랍니다.');
            }
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
            this.allSelected = false;
            this.menuSelectList();
        }
    },

    mounted() {
        this.menuSelectList();
    },
}

export default App;
</script>