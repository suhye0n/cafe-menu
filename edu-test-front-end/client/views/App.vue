<template>
    <Header></Header>
    <div>
        <router-view/>
    </div>
    <Footer></Footer>
</template>

<script>
    import Header from './layout/Header.vue';
    import Footer from './layout/Footer.vue';
    import Main from './pages/main/main.vue';

    const App = {
        data: () => {
            dataTest: 2
            dataTest2: 2
        },
        methods: {
            
        },
        watch: {
            dataTest: function (oldValue, newValue) {
                
            }
        },
        computed: {
            dataTestAdd: function () {
                return dataTest + dataTest2;
            }
        },
        components: {
            'Header': Header,
            'Main': Main,
            'Footer': Footer,
        },
        mounted: () => {
            console.log("App.vue가 초기화됨");
        }
    }

    export default App;
</script>

<style></style>