<template>
    <div class="cafe">
        <div>
            <select v-model="searchType">
                <option value="상품명">상품명</option>
                <option value="작성자">작성자</option>
                <option value="작성일">작성일</option>
            </select>
            <input type="text" v-model="searchKeyword" placeholder="입력해주세요.">
            <button @click="menuSearch()">검색</button>
        </div>
        <div>
            상품명:
            <input type="text" v-model="menu.product_name">
        </div>
        <div>
            카테고리 : 
            <input type="radio" name="category" value="아메리카노" v-model="menu.product_category">아메리카노
            <input type="radio" name="category" value="카페라떼" v-model="menu.product_category">카페라떼
            <input type="radio" name="category" value="에이드" v-model="menu.product_category">에이드
            <input type="radio" name="category" value="스무디" v-model="menu.product_category">스무디
            <input type="radio" name="category" value="프라푸치노" v-model="menu.product_category">프라푸치노
            <input type="radio" name="category" value="콜라" v-model="menu.product_category">콜라
        </div>
        <div>
            가격 : <input type="number" v-model="menu.product_price">
        </div>
        <div>
            작성자 : <input type="text" v-model="menu.product_writer">
        </div>
        <div>
            <input type="radio" name="temp" value="아이스" v-model="menu.product_explan">아이스
            <input type="radio" name="temp" value="핫" v-model="menu.product_explan">핫
        </div>
        <div>
            <button @click="menuInsert()">등록</button>
        </div>
        <div>-------------------------------------------</div>
        <table style="border: 1px solid #000; text-align: center;">
            <thead>
                <tr>
                    <th style="border: 1px solid #000;">NO</th>
                    <th style="border: 1px solid #000;">상품명</th>
                    <th style="border: 1px solid #000;">메뉴가격(단위:원)</th>
                    <th style="border: 1px solid #000;">작성자</th>
                    <th style="border: 1px solid #000;">작성일</th>
                    <th style="border: 1px solid #000;">카테고리</th>
                    <th style="border: 1px solid #000;">핫/아이스</th>
                    <th style="border: 1px solid #000;">수정</th>
                    <th style="border: 1px solid #000;">삭제</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, idx) in menuList">
                    <td style="border: 1px solid #000;">{{idx}}</td>
                    <td style="border: 1px solid #000;">{{item.product_name}}</td>
                    <td style="border: 1px solid #000;">{{item.product_price}}</td>
                    <td style="border: 1px solid #000;">{{item.product_writer}}</td>
                    <td style="border: 1px solid #000;">{{item.product_date}}</td>
                    <td style="border: 1px solid #000;">{{item.product_category}}</td>
                    <td style="border: 1px solid #000;">{{item.product_explan}}</td>
                    <td style="border: 1px solid #000;"><button @click="menuUpdate(item)">수정</button></td>
                    <td style="border: 1px solid #000;"><button @click="menuDelete(item)">삭제</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';

    const App = {
        data: () => {
            return {
                testNumber: 1,
                testString: '문자열',

                menu: {
                    product_name: null,
                    product_price: 0,
                    product_category: null,
                    product_explan: null,
                    product_writer: null,
                    product_date: null,
                    searchType: "상품명",
                    searchKeyword: "",
                },

                menuList: [],
            }
        },
        methods: {
            menuInsert: function () {
                let vm = this;
                vm.menu.product_date = new Date().toISOString().slice(0, 10);
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
                let searchParams = {
                    searchType: vm.searchType,
                    searchKeyword: vm.searchKeyword,
                };

                axios({
                    url: '/menuSelectList.request',
                    method: 'get',
                    params: searchParams,
                }).then(function (response) {
                    vm.menuList = response.data;
                });
            },

            menuSearch: function () {
                this.menuSelectList();
            },

            menuUpdate: function (item) {
                let vm = this;
                axios({
                    url: '/menuUpdate.request',
                    method: 'post',
                    data: item,
                }).then(function (response) {
                    if (response.data > 0) {
                        vm.menuSelectList();
                    }
                }).catch(function (error) {
                    alert('메뉴 수정 에러가 발생하였습니다.');
                });
            },

            menuDelete: function (item) {
                let vm = this;
                axios({
                    url: '/menuDelete.request',
                    method: 'post',
                    data: { product_index: item.product_index },
                }).then(function (response) {
                    if (response.data > 0) {
                        vm.menuSelectList();
                    }
                }).catch(function (error) {
                    alert('메뉴 삭제 에러가 발생하였습니다.');
                });
            },
            
        },
        mounted() {
            console.log('메뉴관리화면이 마운트 됨');
            this.menuSelectList();
        },
    }

    export default App;
</script>