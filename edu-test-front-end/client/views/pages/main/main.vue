<template>
    <div>
        메인화면입니다.
    </div>

    <!-- 수정: 이미지 왼쪽/오른쪽 컨트롤러로도 슬라이드 할 수 있고, 하단 동그라미 페이징 버튼 눌러서도 슬라이드 할 수 있도록... -->
    <div class="slide">
        <ul class="slides">
            <li><img src="/client/resources/img/1.jpg" /></li>
            <li><img src="/client/resources/img/2.jpg" /></li>
            <li><img src="/client/resources/img/3.jpg" /></li>
        </ul>
        <p class="controller">
            <span class="prev">&lang;</span>
            <span class="next">&rang;</span>
        </p>
    </div>
    <!-- // 수정 -->

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