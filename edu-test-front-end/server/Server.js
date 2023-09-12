const {BASE_DIR} = require("../Global");

const express = require('express');
const webServer = express();
const expressProxy = require('express-http-proxy');

//파일 시스템 관련 라이브러리
const FS = require('fs');
//stream: 특정 자원을 Streaming 하기 위한 라이브러리 => Transform: Streaming 중인 자원의 Data에 Data 수정 및 추가를 지원해주는 객체
const Transform = require('stream').Transform;
//Streaming 중인 자원에 새로운 데이터를 stream 공간에 추가하기 위한 라이브러리
const newLineStream = require('new-line');

webServer.listen(8000, function () {
    console.log("Node.js를 활용한 Express Web Server");
})

webServer.get('/', function (request, response) {
    console.log("Root URL");
    response.sendFile("C:/Users/claph/Documents/GitHub/cafe-menu/edu-test-front-end/client/views/pages/index.html")
})

webServer.use('*.request', expressProxy('http://localhost:8080', {
    proxyReqPathResolver: function (request) {
        return `${request.params['0']}.request`;
    }
}))

webServer.get('*.page', function (request, response) {
    const streamTransform = new Transform();
    streamTransform._transform = function (data, encoding, done) {
        let fileContent = data.toString();
        let replaceBeforeContent = `<script id="app-start-view">const APP_START_VIEW_PATH = '/';</script>`;
        let replaceAfterContent = `<script id="app-start-view">const APP_START_VIEW_PATH = '${request.params['0']}.page';</script>`;
        fileContent.replace(replaceBeforeContent, replaceAfterContent);
        this.push(fileContent);
        done();
    }

    FS.createReadStream(`${BASE_DIR}/client/views/pages/index.html`).pipe(newLineStream()).pipe(streamTransform).pipe(response);
})

webServer.get('*.*', function (request, response) {
    response.sendFile(BASE_DIR + request.params['0'] + '.' + request.params['1']);
})