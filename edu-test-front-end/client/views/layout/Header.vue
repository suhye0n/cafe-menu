<template>
    <div class="header">
        <span style="margin-right: 50px">
            <router-link to="/">카페 메뉴관리</router-link>
        </span>
        <span style="margin-right: 10px">
            <router-link to="/cafe.page">메뉴관리</router-link>
        </span>
        <span style="margin-right: 10px">
            <router-link to="/cart.page">상품 주문</router-link>
        </span>
        <span>
            <router-link to="/list.page">주문 내역</router-link>
        </span>
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