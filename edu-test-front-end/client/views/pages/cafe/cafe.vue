<template>
    <div>
        메뉴관리화면입니다.
    </div>
    <div>
        메뉴명 : <input type="text" v-model="menu.product_name">
    </div>
    <div>
        메뉴가격 : <input type="number" v-model="menu.product_price">
    </div>
    <div>
        <button @click="menuInsert()">등록</button>
    </div>
    <div>-------------------------------------------</div>
    <table style="border: 1px solid #000; text-align: center;">
        <thead>
            <tr>
                <th style="border: 1px solid #000;">메뉴명</th>
                <th style="border: 1px solid #000;">메뉴가격(단위:원)</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(item, idx) in menuList">
                <td style="border: 1px solid #000;">{{item.product_name}}</td>
                <td style="border: 1px solid #000;">{{item.product_price}}</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
    import axios from 'axios';

    const App = {
        data: () => {
            return {
                testNumber: 1,
                testString: '문자열',

                menu: {
                    product_name: "",
                    product_price: 0,
                },

                menuList: [],
            }
        },
        methods: {
            menuInsert: function () {
                let vm = this;
                axios({
                    url: '/menuInsert.request',
                    method: 'post',
                    header: {
                        "Content-Type": "application/json; charset=UTF-8",
                    },
                    data: vm.menu,
                }).then(function (response) {
                    console.log('/menuInsert.request의 response 결과 값 : ' + response);
                    if (response.data > 0) {
                        vm.menuSelectList();
                    } else {
                        alert('등록된 메뉴가 없습니다.');
                    }
                }).catch(function (error) {
                    console.log('/menuInsert.request 에러 발생', error);
                    alert('메뉴 등록 에러가 발생하였습니다. 관리자에게 문의바랍니다.');
                })
            },

            menuSelectList: function () {
                let vm = this;
                axios({
                    url: '/menuSelectList.request',
                    method: 'get',
                    header: {
                        "Content-Type": "application/json; charset=UTF-8",
                    },
                    //data: vm.menu,
                }).then(function (response) {
                    console.log('/menuSelectList.request의 response 결과 값 : ', response);
                    vm.menuList = response.data;
                }).catch(function (error) {
                    console.log('/menuSelectList.request 에러 발생', error);
                    alert('메뉴 등록 에러가 발생하였습니다. 관리자에게 문의바랍니다.');
                })
            },
        },
        mounted() {
            console.log('메뉴관리화면이 마운트 됨');
            this.menuSelectList();
        },
    }

    export default App;
</script>