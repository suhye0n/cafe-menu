<template>
    <div class="header">
        <span class="title">
            <router-link to="/">
                <i class="xi-cafe" />
                어서와
            </router-link>
        </span>
        <span style="float: right">
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
            <span>
                <router-link to="/game/pet.page">
                    <i class="xi-calendar-list" />
                    미니게임
                </router-link>
            </span>
            <span>
                <router-link to="/album.page">
                    <i class="xi-calendar-list" />
                    사진첩
                </router-link>
            </span>
            <span>
                <router-link to="/guestbook.page">
                    <i class="xi-calendar-list" />
                    방명록
                </router-link>
            </span>
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