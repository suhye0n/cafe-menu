<template>
    <div class="footer">
        copyrightⓒ 2023 <a href="https://github.com/suhye0n" target="_blank">suhye0n</a> all rights deserved.
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