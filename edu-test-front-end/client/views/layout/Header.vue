<template>
    <div class="header">
        <span class="title">
            <router-link to="/">
                <i class="xi-cafe" />
                카페 메뉴관리
            </router-link>
        </span>
        <span>
            <router-link to="/cafe.page">
                <i class="xi-bars" />
                메뉴관리
            </router-link>
        </span>
        <span>
            <router-link to="/cart.page">
                <i class="xi-cart-o" />
                상품 주문
            </router-link>
        </span>
        <span>
            <router-link to="/list.page">
                <i class="xi-calendar-list" />
                주문 내역
            </router-link>
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
    },
}

export default App;
</script>