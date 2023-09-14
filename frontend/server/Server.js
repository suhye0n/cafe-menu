const { BASE_DIR } = require("../Global");

const express = require('express');
const webServer = express();
const expressProxy = require('express-http-proxy');

const FS = require('fs');
const Transform = require('stream').Transform;
const newLineStream = require('new-line');

webServer.listen(8000, function () {
    console.log("Node.js를 활용한 Express Web Server");
})

webServer.get('/', function (request, response) {
    console.log("Root URL");
    response.sendFile("C:/Users/claph/Documents/GitHub/cafe-menu/frontend/client/views/index.html")
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

    FS.createReadStream(`${BASE_DIR}/client/views/index.html`).pipe(newLineStream()).pipe(streamTransform).pipe(response);
})

webServer.get('*.*', function (request, response) {
    response.sendFile(BASE_DIR + request.params['0'] + '.' + request.params['1']);
})