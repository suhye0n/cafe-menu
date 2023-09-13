<template>
    <div>
        <h3>주문 상세</h3>

        <fieldset v-for="(item, idx) in orderList">
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
                    <tr v-for="(item, idx) in orderList">
                        <td style="border: 1px solid #000;">{{ idx }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_name }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_category }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_product_temperature }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_amount }}</td>
                        <td style="border: 1px solid #000;">{{ item.order_price }}</td>
                        <td style="border: 1px solid #000;"><button @click="menuSelectOne(item)">수정</button></td>
                        <td style="border: 1px solid #000;"><button @click="menuDelete(item.product_index)">삭제</button></td>
                    </tr>
                </tbody>
            </table>
            <button>수령 완료</button>
            <button>주문 취소</button>
            <!-- // 수정 -->
        </fieldset>

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
                <tr v-for="(item, idx) in orderList">
                    <td style="border: 1px solid #000;">{{ idx }}</td>
                    <td style="border: 1px solid #000;">{{ item.order_product_number }}</td>
                    <td style="border: 1px solid #000;">{{ item.order_product_name }}</td>
                    <td style="border: 1px solid #000;">{{ item.order_price }}</td>
                    <td style="border: 1px solid #000;">{{ item.order_date }}</td>
                    <td style="border: 1px solid #000;">{{ item.order_state }}</td>
                    <td style="border: 1px solid #000;"><button @click="menuSelectOne(item)">수정</button></td>
                    <td style="border: 1px solid #000;"><button @click="menuDelete(item.product_index)">삭제</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
const App = {
    data: () => {
        return {
            testNumber: 1,
            testString: '문자열'
        }
    },
    mounted() {
        console.log('메인화면이 마운트 됨');
    },
}

export default App;
</script>