<template>
    <div class="slide">
        <ul class="slides">
            <li :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <img src="/client/resources/img/1.jpg" />
            </li>
            <li :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <img src="/client/resources/img/2.jpg" />
            </li>
            <li :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <img src="/client/resources/img/3.jpg" />
            </li>
        </ul>
        <p class="controller">
            <span @click="prevSlide">&lang;</span>
            <span @click="nextSlide">&rang;</span>
        </p>
        <p class="pagination">
            <span v-for="(slide, index) in slides" :key="index" :class="{ active: index === currentIndex }"
                @click="goToSlide(index)"></span>
        </p>
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
        this.timer = setInterval(this.nextSlide, 5000);
    },
    beforeDestroy() {
        clearInterval(this.timer);
    },
}
</script>
