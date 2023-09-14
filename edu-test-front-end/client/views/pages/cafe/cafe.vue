<template>
    <div class="cafe content">
        <h1>메뉴 관리</h1>

        <div class="search-section">
            <select v-model="selectedSearchOption">
                <option value="product_name">상품명</option>
                <option value="product_writer">작성자</option>
                <option value="product_date">작성일</option>
            </select>

            <input v-if="selectedSearchOption === 'product_date'" type="date" v-model="searchKeyword"
                @keyup.enter="dataSearch" />

            <input v-else type="text" v-model="searchKeyword" placeholder="검색..." @keyup.enter="dataSearch" />

            <button @click="dataSearch()" class="confirm">검색</button>
        </div>

        <hr />

        <div>
            <button @click="toggleContainer">컨테이너 {{ isContainerOpen ? '접기' : '펼치기' }}</button>
        </div>
        <div class="container" v-if="isContainerOpen">
            <div>
                <input class="full" placeholder="상품명" type="text" v-model="menu.product_name">
            </div>
            <div class="margin">
                <input type="radio" name="category" value="아메리카노" v-model="menu.product_category">아메리카노
                <input type="radio" name="category" value="카페라떼" v-model="menu.product_category">카페라떼
                <input type="radio" name="category" value="에이드" v-model="menu.product_category">에이드
                <input type="radio" name="category" value="스무디" v-model="menu.product_category">스무디
                <input type="radio" name="category" value="프라푸치노" v-model="menu.product_category">프라푸치노
                <input type="radio" name="category" value="콜라" v-model="menu.product_category">콜라
            </div>
            <div class="margin">
                <h5>가격</h5>
                <input type="number" v-model="menu.product_price">
            </div>
            <div>
                <h5>작성자</h5>
                <input type="text" v-model="menu.product_writer">
            </div>
            <div class="margin">
                <input type="checkbox" value="아이스" v-model="menu.product_ice"> 아이스
                <input type="checkbox" value="핫" v-model="menu.product_hot"> 핫
            </div>
            <div>
                <textarea class="full" placeholder="상세 설명" v-model="menu.product_explan" />
            </div>
            <div class="margin">
                <button @click="handleButtonAction()" class="confirm">{{ buttonLabel }}</button>
                <button v-if="isEditMode" class="button" id="addBtn" @click="resetForm()">취소</button>
            </div>
        </div>

        <div>
            <table>
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>상품명</th>
                        <th>메뉴가격(단위:원)</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>카테고리</th>
                        <th>설명</th>
                        <th>핫</th>
                        <th>아이스</th>
                        <th>수정</th>
                        <th>삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, idx) in menuList">
                        <td>{{ idx }}</td>
                        <td>{{ item.product_name }}</td>
                        <td>{{ item.product_price }}</td>
                        <td>{{ item.product_writer }}</td>
                        <td>{{ item.product_date }}</td>
                        <td>{{ item.product_category }}</td>
                        <td>{{ item.product_explan }}</td>
                        <td>{{ item.product_hot }}</td>
                        <td>{{ item.product_ice }}</td>
                        <td><button @click="menuSelectOne(item); this.isContainerOpen = true;" class="confirm">수정</button></td>
                        <td><button @click="menuDelete(item.product_index)">삭제</button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

const App = {
    data: () => {
        return {
            testNumber: 1,
            testString: '문자열',
            isContainerOpen: true,

            emptyMenu: {
                product_index: null,
                product_name: null,
                product_category: null,
                product_price: null,
                product_writer: null,
                product_explan: null,
                product_ice: false,
                product_hot: false,
                product_date: null
            },

            menu: {
                product_name: null,
                product_price: 0,
                product_category: null,
                product_explan: null,
                product_ice: false,
                product_hot: false,
                product_writer: null,
                product_date: null,
            },

            searchKeyword: "",
            selectedSearchOption: 'product_name',

            menuList: [],
            dataList: [],

            selectedRow: null,

            isEditMode: false
        }
    },

    methods: {
        toggleContainer() {
            this.isContainerOpen = !this.isContainerOpen;
        },

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
                    alert("상품이 등록되었습니다.");
                    vm.menuSelectList();
                    vm.menu = vm.emptyMenu;
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
            }).then(function (response) {
                vm.menuList = response.data;
            });
        },

        dataSearch() {
            const vm = this;
            let searchPayload = {
                keyword: vm.searchKeyword,
                option: vm.selectedSearchOption
            };
            axios({
                url: "/menuSearch.request",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: searchPayload,
            })
                .then(function (response) {
                    console.log("dataSearch - response : ", response.data);
                    vm.menuList = response.data;
                })
                .catch(function (error) {
                    console.log("dataSearch - error : ", error);
                    alert("상품 검색에 오류가 발생했습니다. 관리자에게 문의바랍니다.");
                });
        },

        menuSelectOne: function (item) {
            this.selectedRow = item.product_index;

            this.isEditMode = true;

            this.menu = {
                product_index: item.product_index,
                product_name: item.product_name,
                product_category: item.product_category,
                product_price: item.product_price,
                product_writer: item.product_writer,
                product_explan: item.product_explan,
                product_hot: item.product_hot,
                product_ice: item.product_ice,
                product_date: item.product_date
            };
        },

        resetForm: function () {
            this.menu = this.emptyMenu;
            this.isEditMode = false;
        },

        menuUpdate: function (item) {
            let vm = this;
            axios({
                url: '/menuUpdate.request',
                method: 'post',
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.menu,
            }).then(function (response) {
                if (response.data > 0) {
                    vm.menuSelectList();
                    vm.resetForm();
                }
            }).catch(function (error) {
                console.log('/menuUpdate.request 에러 발생', error);
                alert('메뉴 수정 에러가 발생하였습니다.');
            });
        },

        menuDelete: function (item) {
            console.log(item);
            let vm = this;
            axios({
                url: '/menuDelete.request',
                method: 'post',
                data: { product_index: item },
            }).then(function (response) {
                alert('메뉴 삭제가 완료되었습니다.');
                console.log(response);
                if (response.data > 0) {
                    vm.menuSelectList();
                }
            }).catch(function (error) {
                alert('메뉴 삭제 에러가 발생하였습니다.');
            });
        },

        handleButtonAction() {
            if (!this.menu.product_name) {
                alert("상품명을 입력해 주세요.");
                return;
            }

            if (!this.menu.product_category) {
                alert("카테고리를 선택해 주세요.");
                return;
            }

            if (!this.menu.product_price) {
                alert("가격을 입력해 주세요.");
                return;
            }

            if (!this.menu.product_writer) {
                alert("작성자를 입력해 주세요.");
                return;
            }

            if (this.isEditMode) {
                this.menuUpdate();
            } else {
                this.menuInsert();
            }
        },

    },
    watch: {
        "selectedSearchOption": function (newValue, oldValue) {
            this.searchKeyword = "";
        }
    },
    computed: {
        buttonLabel() {
            return this.isEditMode ? '수정' : '등록';
        }
    },
    mounted() {
        console.log('메뉴관리화면이 마운트 됨');
        this.menuSelectList();
    },
}

export default App;
</script>