<template>
    <div class="slide">
        <ul class="slides">
            <li :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <img src="/client/resources/img/1.png" />
            </li>
            <li :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <img src="/client/resources/img/2.jpg" />
            </li>
            <li :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <img src="/client/resources/img/3.png" />
            </li>
        </ul>
        <p class="controller">
            <span @click="prevSlide"><i class="xi-arrow-left" /></span>
            <span @click="nextSlide"><i class="xi-arrow-right" /></span>
        </p>
        <p class="pagination">
            <span v-for="(slide, index) in slides" :key="index" :class="{ active: index === currentIndex }"
                @click="goToSlide(index)"></span>
        </p>
    </div>

    <div class="main content">
        <div class="container">
            <h2 style="color: #5C3916">인삿말 <i class="xi-emoticon-smiley-o" /></h2>
            <p>안녕하세요, <b style="color: #B27C47">카페 메뉴관리</b>입니다.</p>
        </div>
        
        <div class="container">
            <h2 style="color: #5C3916">설명서 <i class="xi-help-o" /></h2>
            <h4 style="color: #B27C47">메뉴 관리</h4>
            <ul>
                <li class="ml">상품명/작성자/작성일로 메뉴를 검색할 수 있습니다.</li>
                <li class="ml">상품을 등록할 수 있습니다.</li>
                <li class="ml">상품 등록 컨테이너를 접을 수 있습니다.</li>
                <li class="ml">등록된 상품 리스트를 볼 수 있습니다.</li>
                <li class="ml">등록된 상품을 수정/삭제할 수 있습니다.</li>
            </ul>
            
            <h4 style="color: #B27C47">상품 주문</h4>
            <ul>
                <li class="ml">주문 가능한 상품 리스트를 볼 수 있습니다.</li>
                <li class="ml">상품을 장바구니에 추가할 수 있습니다.</li>
                <li class="ml">장바구니에 추가한 상품을 주문할 수 있습니다.</li>
            </ul>
            
            
            <h4 style="color: #B27C47">주문 내역</h4>
            <ul>
                <li class="ml">상품 주문 리스트를 볼 수 있습니다.</li>
                <li class="ml">주문을 처리/취소할 수 있습니다.</li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            currentIndex: 0,
            slides: [1, 2, 3],
            timer: null,
        };
    },
    methods: {
        nextSlide() {
            if (this.currentIndex < this.slides.length - 1) {
                this.currentIndex++;
            } else {
                this.currentIndex = 0;
            }
        },
        prevSlide() {
            if (this.currentIndex > 0) {
                this.currentIndex--;
            } else {
                this.currentIndex = this.slides.length - 1;
            }
        },
        goToSlide(index) {
            this.currentIndex = index;
        },
    },
    mounted() {
        console.log('메인화면이 마운트 됨');
        this.timer = setInterval(this.nextSlide, 2000);
    },
    beforeDestroy() {
        clearInterval(this.timer);
    },
}
</script>
