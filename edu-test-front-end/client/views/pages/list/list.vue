<template>
    <div class="list content">
        <h1>
            <i class="xi-spinner-3 xi-spin" /> 진행중인 주문
        </h1>

        <div v-for="(groupedOrder, orderNumber) in groupedOrders">
            <div class="container" v-show="isOrderInProgress[orderNumber]">
                <h3>주문 번호: {{ orderNumber }}번</h3>

                <table>
                    <thead>
                        <tr>
                            <th>NO</th>
                            <th>상품명</th>
                            <th>카테고리</th>
                            <th>온도</th>
                            <th>수량</th>
                            <th>주문금액</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, idx) in groupedOrder">
                            <td>{{ idx }}</td>
                            <td>{{ item.order_product_name }}</td>
                            <td>{{ item.order_product_category }}</td>
                            <td>{{ item.order_product_temperature }}</td>
                            <td>{{ item.order_amount }}</td>
                            <td>{{ item.order_price }}</td>
                        </tr>
                    </tbody>
                </table>

                <h4>합계: {{ calculateTotalPrice(groupedOrder) }}원</h4>

                <button @click="orderUpdate(orderNumber)" class="confirm"><i class="xi-star-o" /> 수령 완료</button>
                <button @click="orderDelete(orderNumber)"><i class="xi-close" /> 주문 취소</button>
            </div>
        </div>

        <h1>
            <i class="xi-calendar-list" /> 주문 내역
        </h1>
        <table>
            <thead>
                <tr>
                    <th>주문번호</th>
                    <th>주문상품</th>
                    <th>주문금액</th>
                    <th>주문일</th>
                    <th>주문상태</th>
                </tr>
            </thead>
            <tbody>
                <template v-for="(groupedOrder, orderNumber) in groupedOrders">
                    <tr>
                        <td>{{ groupedOrder[0].order_product_number }}</td>
                        <td>
                            {{ groupedOrder[0].order_product_name }}
                            <span v-if="groupedOrder.length > 1"> 외 {{ groupedOrder.length - 1 }}개</span>
                        </td>
                        <td>
                            {{ groupedOrder.reduce((sum, item) => sum + item.order_price, 0) }}
                        </td>
                        <td>{{ groupedOrder[0].order_date }}</td>
                        <td>{{ groupedOrder[0].order_state }}</td>
                    </tr>
                </template>
            </tbody>

        </table>
    </div>
</template>
  
<script>
import axios from 'axios';

const App = {
    data: () => ({
        orderList: [],
    }),

    computed: {
        groupedOrders() {
            return this.orderList.reduce((acc, current) => {
                const orderNumber = current.order_product_number;
                if (!acc[orderNumber]) {
                    acc[orderNumber] = [];
                }
                acc[orderNumber].push(current);
                return acc;
            }, {});
        },
        isOrderInProgress() {
            return Object.keys(this.groupedOrders).reduce((acc, orderNumber) => {
                const orders = this.groupedOrders[orderNumber];
                acc[orderNumber] = orders.some(order => order.order_state === "진행중");
                return acc;
            }, {});
        }
    },
    methods: {
        calculateTotalPrice(groupedOrder) {
            return groupedOrder.reduce((sum, item) => sum + item.order_price, 0);
        },
        orderInsert: function () {
            let vm = this;
            vm.order.product_date = new Date().toISOString().slice(0, 10);
            axios({
                url: '/orderInsert.request',
                method: 'post',
                header: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.order,
            }).then(function (response) {
                console.log('/orderInsert.request의 response 결과 값 : ' + response);
                if (response.data > 0) {
                    alert("상품이 등록되었습니다.");
                    vm.orderSelectList();
                    vm.order = vm.emptyorder;
                } else {
                    alert('등록된 메뉴가 없습니다.');
                }
            }).catch(function (error) {
                console.log('/orderInsert.request 에러 발생', error);
                alert('메뉴 등록 에러가 발생하였습니다. 관리자에게 문의바랍니다.');
            })
        },

        orderSelectList: function () {
            let vm = this;
            let searchParams = {
                searchType: vm.searchType,
                searchKeyword: vm.searchKeyword,
            };

            axios({
                url: '/orderSelectList.request',
                method: 'get',
                params: searchParams,
            }).then(function (response) {
                vm.orderList = response.data;
            });
        },

        orderSearch: function () {
            this.orderSelectList();
        },

        orderSelectOne: function (item) {
            this.selectedRow = item.product_index;

            this.isEditMode = true;

            this.order = {
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
            this.order = this.emptyorder;
            this.isEditMode = false;
        },

        orderUpdate: function (orderNumber) {
            let vm = this;
            axios({
                url: '/orderUpdate.request',
                method: 'post',
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { order_product_number: orderNumber, order_state: '완료' },
            }).then(function (response) {
                if (response.data > 0) {
                    alert('주문이 완료 처리되었습니다.');
                    vm.orderSelectList();
                } else {
                    alert('주문 완료 처리에 실패했습니다.');
                }
            }).catch(function (error) {
                console.log('/orderUpdate.request 에러 발생', error);
                alert('주문 완료 처리에 에러가 발생하였습니다.');
            });
        },

        orderDelete: function (orderNumber) {
            let vm = this;
            axios({
                url: '/orderDelete.request',
                method: 'post',
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { order_product_number: orderNumber },
            }).then(function (response) {
                if (response.data > 0) {
                    alert('주문이 취소되었습니다.');
                    vm.orderSelectList();
                } else {
                    alert('주문 취소에 실패했습니다.');
                }
            }).catch(function (error) {
                console.log('/orderDelete.request 에러 발생', error);
                alert('주문 취소 처리에 에러가 발생하였습니다.');
            });
        },


    },
    mounted() {
        console.log('메뉴관리화면이 마운트 됨');
        this.orderSelectList();
    },
}

export default App;
</script>