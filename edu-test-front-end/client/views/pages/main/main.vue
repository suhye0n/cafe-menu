<template>
    <!-- 이미지 슬라이더 -->
    <div class="slider-container">
        <div class="slide fade">
            <img class="slide-image" src="/client/resources/img/1.jpg">
        </div>

        <div class="slide fade">
            <img class="slide-image" src="/client/resources/img/2.jpg">
        </div>

        <div class="slide fade">
            <img class="slide-image" src="/client/resources/img/3.jpg">
            <div class="slide-content">
            </div>
        </div>

        <div class="slider-nav">
            <a class="slider-nav-btn" onclick="plusSlides(-1)">
                &lang;
            </a>
            <a class="slider-nav-btn" onclick="plusSlides(1)">
                <span class="next">&rang;</span>
            </a>
        </div>

        <div class="dot-container">
            <span class="dot" onclick="currentSlide(1)"></span>
            <span class="dot" onclick="currentSlide(2)"></span>
            <span class="dot" onclick="currentSlide(3)"></span>
        </div>
    </div>
</template>

<script>
const App = {
    data: () => {
        return {
            testNumber: 1,
            testString: '문자열',
            slideIndex: 1,
            slides: document.getElementsByClassName("slide"),
            dots: document.getElementsByClassName("dot")
        }
    },

    methods: {
        showSlides: function (n) {
            if (n > slides.length) {
                slideIndex = 1;
            }
            if (n < 1) {
                slideIndex = slides.length;
            }
            for (let i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            for (let i = 0; i < dots.length; i++) {
                dots[i].className = dots[i].className.replace(" active", "");
            }
            slides[slideIndex - 1].style.display = "block";
            dots[slideIndex - 1].className += " active";
        },
        plusSlides: function (n) {
            showSlides((slideIndex += n));
        },
        currentSlide: function (n) {
            showSlides((slideIndex = n));
        },
    },

    mounted() {
        console.log('메인화면이 마운트 됨');

        // setInterval(function () {
        //     plusSlides(1);
        // }, 5000),

        // showSlides(slideIndex);
    },
}

export default App;
</script>