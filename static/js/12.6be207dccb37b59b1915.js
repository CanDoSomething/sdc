webpackJsonp([12],{"K+Dp":function(t,e,n){var s=n("mxmy");"string"==typeof s&&(s=[[t.i,s,""]]),s.locals&&(t.exports=s.locals);n("rjj0")("47424920",s,!0,{})},Yid5:function(t,e,n){"use strict";function s(t){n("K+Dp")}Object.defineProperty(e,"__esModule",{value:!0});var i=n("vLgD"),o=n("//Fk"),a=n.n(o),r=n("mvHQ"),d=n.n(r),c=n("Dd8w"),u=n.n(c),g=n("NYxO"),A=n("X2Oc"),h={name:"chartMixin",data:function(){return{ws:null,otherImg:"",selfImg:"",nowStuCode:"",nowTeaCode:"",stuIsInGroup:!1,teaIsInGroup:!1}},computed:u()({},Object(g.b)({userType:function(t){return t.user.userType},openid:function(t){return t.user.openid}})),mounted:function(){this.getOpenid()},watch:{courseId:function(){this.getOpenid()}},methods:{getOpenid:function(){var t=this;void 0!==this.courseId&&""!==this.courseId&&Object(i.a)({url:"course/getOnClassUserOpenid?courseId="+this.courseId,method:"get",data:{artId:this.$route.params.id,openid:this.$store.state.user.openid}}).then(function(e){0===e.data.code&&(t.nowTeaCode=e.data.data.teaOpenid,t.nowStuCode=e.data.data.stuOpenid,Object(i.a)({url:"user/queryUserInfo?openid="+t.nowTeaCode,method:"get"}).then(function(e){var n=t.nowTeaCode===t.openid?"self":"other";if(0===Number(e.data.code)){var s=e.data.data;t[n+"Img"]=s.stuHeadimgurl||s.teaHeadimgurl}Object(i.a)({url:"user/queryUserInfo?openid="+t.nowStuCode,method:"get"}).then(function(e){var n=t.nowTeaCode!==t.openid?"self":"other";if(t.initWebsocket(),0===Number(e.data.code)){var s=e.data.data;t[n+"Img"]=s.stuHeadimgurl||s.teaHeadimgurl}})}))})},initWebsocket:function(){if(null===this.ws&&""!==this.courseId){var t="ws://47.93.225.12:8082/socketServer/"+this.openid;this.ws=new WebSocket(t),this.ws.onopen=this.websocketonopen,this.ws.onmessage=this.websocketonmessage,this.ws.onerror=this.websocketonerror,this.ws.onclose=this.websocketclose}},websocketonopen:function(){this.checkIsIn()},websocketonmessage:function(t){var e=JSON.parse(t.data);this.recSocketMsg(e)},websocketonerror:function(){this.initWebSocket()},websocketsend:function(t){this.ws.send(d()(t))},websocketclose:function(t){console.log("断开连接",t)},checkIsIn:function(){var t={groupname:this.courseId,opttype:"GetChatGroupUserList",username:this.openid};this.websocketsend(t)},teaJoinGroup:function(){var t={addusername:this.nowStuCode,groupname:this.courseId,opttype:"CreateChatGroup",username:this.openid};this.websocketsend(t),this.loading=!1},getHis:function(){var t={groupname:this.courseId,opttype:"GetChatGroupChatRecordList",username:this.openid};this.websocketsend(t)},sendSocketMsg:function(t){var e={content:t,groupname:this.courseId,opttype:"SendToChatGroup",username:this.openid};this.websocketsend(e)},recSocketMsg:function(t){var e=this;"GetChatGroupUserList"===t.opttype?0!==t.errno?"teacher"===this.userType?this.teaJoinGroup():Object(A.b)(this,"请退出,等待老师开课"):(this.loading=!1,this.getHis()):"GetChatGroupChatRecordList"===t.opttype?(this.loading=!1,0===t.errno?this.renderHis(t.list):Object(A.b)(this,t.errmsg),this.$nextTick(function(){e.loading=!1})):"SendToChatGroup"===t.opttype&&this.renderChart(t)},renderHis:function(t){var e=this;console.log("list",t),t=t.sort(function(t,e){return Number(t.inserttime)-Number(e.inserttime)}),t.map(function(t){var n=JSON.parse(t.content);n.time=new Date(t.inserttime),e.renderChart(n)})},renderChart:function(t){var e=this,n=t.username===this.openid?"self":"other";console.log("re",this[n+"Img"]),""===this[n+"Img"]?new a.a(function(s){e.getInfo(t.username,n,s)}).then(function(){e.sendMsgFn(n,t.content,t.time)}):this.sendMsgFn(n,t.content,t.time)},getInfo:function(t,e,n){"self"===e&&""===this.selfImg&&this._getImg(t,"self",n),"other"===e&&""===this.otherImg&&this._getImg(t,"other",n)},_getImg:function(t,e,n){var s=this;Object(i.a)({url:"user/queryUserInfo?openid="+t,method:"get"}).then(function(t){if(n(),0===Number(t.data.code)){var i=t.data.data;s[e+"Img"]=i.stuHeadimgurl||i.teaHeadimgurl}},20)}}},m=n("8pLc"),p=n("KgXo"),l=(m.a,p.a,{name:"chart",components:{noData:m.a,loading:p.a},mixins:[h],data:function(){return{loading:!0,noCourse:!1,courseName:"",teaName:"",courseTime:"",chartList:[],sendMsg:"",courseId:"",doingList:[],count:0,isEnd:!1}},computed:{btnDisabled:function(){return""===this.sendMsg},teaOpenid:function(){return this.$store.state.user.infoObj.teaOpenid||""},stuOpenid:function(){return this.$store.state.user.infoObj.stuOpenid||""},userType:function(){return this.$store.state.user.userType}},mounted:function(){this.$store.commit("SET_RETURN_TEXT","在线交流"),this.$store.commit("SET_RETURN_URL","/my"),this.getDoingList()},watch:{userType:function(){this.getDoingList()}},methods:{getCourseCount:function(){var t=this;Object(i.a)({url:"course/getCountDown?courseId="+this.courseId,method:"get"}).then(function(e){0===e.data.code&&("end"===e.data.data?t.showEnd():(t.count=Number(e.data.data),t.startCount()))})},checkEnd:function(){var t=this;Object(i.a)({url:"course/onCourseEnd?courseId="+this.courseId,method:"get"}).then(function(e){0===e.data.code&&(e.data.data=!0,t.isEnd=e.data.data,e.data.data?t.showEnd():t.startCount())})},startCount:function(){var t=this;setTimeout(function(){t.checkEnd()},1e3*this.count)},showEnd:function(){this.isEnd=!0,Object(A.c)(this,"课程已结束")},clickSend:function(){this.isEnd||(this.sendSocketMsg(this.sendMsg),this.sendMsgFn("self",this.sendMsg))},sendMsgFn:function(t,e,n){if(""!==e){var s=n||new Date;s=s.toTimeString().split(" ")[0],this.chartList.push({time:s,role:t,text:e}),this.$nextTick(function(){document.querySelector(".chart-list-scroll").scrollTop=999999}),"self"===t&&(this.sendMsg="")}},getTime:function(t){return t.split(" ")[1].split(":")[0]+":"+t.split(" ")[1].split(":")[1]},getDoingList:function(){var t=this;this.loading=!0;var e="";"teacher"===this.userType?(e="tea/findTeaHistoryCourse",e+="?teaOpenid="+this.teaOpenid+"&page=1&pageSize=1000"):"student"===this.userType&&(e="stu/lookHistory",e+="?stuOpenid="+this.stuOpenid+"&page=1&pageSize=1000"),Object(i.a)({url:e,method:"get"}).then(function(e){t.loading=!1,0===e.data.code&&void 0!==e.data.data?(t.doingList=e.data.data.filter(function(e){return"teacher"===t.userType?302===e.teaCourse.courseStatus:302===e.teaCourse.courseStatus&&401===e.subStatus}).map(function(e){return e.teaCourse.courseTime=e.teaCourse.courseDate.split(" ")[0]+" "+t.getTime(e.teaCourse.courseStartTime)+" - "+t.getTime(e.teaCourse.courseEndTime),e}),0===t.doingList.length?(t.noCourse=!0,Object(A.b)(t,"请退出等待开课")):(t.courseId=t.doingList[0].teaCourse.courseId,t.courseName=t.doingList[0].teaCourse.courseName,t.courseTime=t.doingList[0].teaCourse.courseTime,"student"===t.userType?t.teaName=t.doingList[0].teaName:t.teaName=t.doingList[0].teaBase.teaName,t.noCourse=!1,t.getCourseCount())):(t.noCourse=!0,Object(A.b)(t,"请退出等待开课"))})}}}),C=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"doing-course"},[!t.loading&&t.noCourse?n("div",{staticClass:"no-course"},[n("no-data",{attrs:{text:"当前没有正在上的课"}})],1):t._e(),t._v(" "),t.loading||t.noCourse?t._e():n("div",{staticClass:"course-info"},[n("div",{staticClass:"course-name"},[n("label",[t._v("课程名称：")]),t._v(" "),n("span",[t._v(t._s(t.courseName))])]),t._v(" "),n("div",{staticClass:"course-time"},[n("label",[t._v("课程时间：")]),t._v(" "),n("span",[t._v(t._s(t.courseTime))])]),t._v(" "),n("div",{staticClass:"course-time"},[n("label",[t._v("老师姓名：")]),t._v(" "),n("span",[t._v(t._s(t.teaName))])])]),t._v(" "),t.loading||t.noCourse?t._e():n("div",{staticClass:"chart-box"},[n("div",{staticClass:"chart-list-scroll"},[t._l(t.chartList,function(e){return 0!==t.chartList.length?n("div",{staticClass:"chart-list"},["self"===e.role?n("div",{staticClass:"msg-self msg-item"},[n("img",{staticClass:"user-icon",attrs:{src:t.selfImg}}),t._v(" "),n("div",{staticClass:"msg-content"},[n("div",{staticClass:"msg-text"},[t._v(t._s(e.text))]),t._v(" "),n("div",{staticClass:"msg-time"},[t._v(t._s(e.time))])])]):t._e(),t._v(" "),"other"===e.role?n("div",{staticClass:"msg-other msg-item"},[n("img",{staticClass:"user-icon",attrs:{src:t.otherImg}}),t._v(" "),n("div",{staticClass:"msg-content"},[n("div",{staticClass:"msg-text"},[t._v(t._s(e.text))]),t._v(" "),n("div",{staticClass:"msg-time"},[t._v(t._s(e.time))])])]):t._e()]):t._e()}),t._v(" "),0===t.chartList.length?n("div",{staticClass:"chart-list chart-empty"},[t._v("\n        暂无聊天记录\n      ")]):t._e()],2)]),t._v(" "),t.loading||t.noCourse?t._e():n("div",{staticClass:"send-msg"},[n("div",{staticClass:"send-box"},[n("input",{directives:[{name:"model",rawName:"v-model",value:t.sendMsg,expression:"sendMsg"}],attrs:{type:"text",disabled:t.isEnd},domProps:{value:t.sendMsg},on:{keyup:function(e){return"button"in e||!t._k(e.keyCode,"enter",13,e.key,"Enter")?t.clickSend(e):null},input:function(e){e.target.composing||(t.sendMsg=e.target.value)}}}),t._v(" "),n("div",{staticClass:"send-btn",class:{"btn-disabled":t.isEnd||t.btnDisabled},on:{click:function(e){return e.stopPropagation(),t.clickSend(e)}}},[t._v("发送")])])]),t._v(" "),t.loading?[n("loading")]:t._e()],2)},f=[],v={render:C,staticRenderFns:f},B=v,b=n("VU/8"),x=s,I=b(l,B,!1,x,"data-v-0c573004",null);e.default=I.exports},mxmy:function(t,e,n){e=t.exports=n("FZ+f")(!0),e.push([t.i,"\n.green[data-v-0c573004] {\n  color: #03b349;\n}\n.doing-course[data-v-0c573004] {\n  padding-bottom: 50px;\n}\n.course-info[data-v-0c573004] {\n  line-height: 2;\n  margin: 10px 0;\n  background: #fff;\n  padding: 10px 20px;\n}\n.chart-box[data-v-0c573004] {\n  height: calc(100% - 140px);\n  overflow: hidden;\n  line-height: 2;\n  margin: 10px 0 0 0;\n  padding: 10px 0 0 0;\n}\n.chart-list-scroll[data-v-0c573004] {\n  padding: 0 20px;\n  height: 100%;\n  overflow: auto;\n}\n.chart-empty[data-v-0c573004] {\n  color: #aaa;\n  padding: 80px 0;\n  text-align: center;\n}\n.msg-item[data-v-0c573004] {\n  position: relative;\n  margin-bottom: 14px;\n}\n.msg-item .user-icon[data-v-0c573004] {\n    position: absolute;\n    width: 30px;\n    height: 30px;\n    border: 2px solid #03b349;\n    border-radius: 40px;\n}\n.msg-item .msg-time[data-v-0c573004] {\n    line-height: 1.3;\n    color: #aaa;\n}\n.msg-item .msg-text[data-v-0c573004] {\n    min-height: 26px;\n    display: inline-block;\n    background: #fff;\n    border: 1px solid #03b349;\n    border-radius: 5px;\n    padding: 0 20px;\n    max-width: 100%;\n    word-break: break-all;\n    text-align: left;\n}\n.msg-item.msg-self .user-icon[data-v-0c573004] {\n    right: 0;\n}\n.msg-item.msg-self .msg-content[data-v-0c573004] {\n    padding-right: 50px;\n    text-align: right;\n}\n.msg-item.msg-other .user-icon[data-v-0c573004] {\n    left: 0;\n}\n.msg-item.msg-other .msg-content[data-v-0c573004] {\n    padding-left: 50px;\n}\n.send-msg[data-v-0c573004] {\n  position: fixed;\n  bottom: 0;\n  width: 100%;\n  line-height: 2;\n  background: #fbf9fe;\n  padding: 0 0 6px 0;\n}\n.send-msg .send-box[data-v-0c573004] {\n    border: 1px solid #eaeaea;\n    border-width: 1px 0;\n    background: #fff;\n    height: 40px;\n    position: relative;\n}\n.send-msg input[data-v-0c573004] {\n    height: 40px;\n    width: calc(100% - 80px);\n    font-size: 16px;\n    padding: 0 70px 0 10px;\n    outline: 0;\n    border: 0;\n}\n.send-msg .send-btn[data-v-0c573004] {\n    background: #03b349;\n    position: absolute;\n    top: 0;\n    right: 10px;\n    color: #fff;\n    padding: 0px 10px;\n    margin: 8px 0;\n    border-radius: 5px;\n}\n.send-msg .send-btn.btn-disabled[data-v-0c573004] {\n      background: #eee;\n      color: #aaa;\n}\n","",{version:3,sources:["C:/Users/zwj/Desktop/sdc-前端最新代码/src/chart/index.vue"],names:[],mappings:";AACA;EACE,eAAe;CAChB;AACD;EACE,qBAAqB;CACtB;AACD;EACE,eAAe;EACf,eAAe;EACf,iBAAiB;EACjB,mBAAmB;CACpB;AACD;EACE,2BAA2B;EAC3B,iBAAiB;EACjB,eAAe;EACf,mBAAmB;EACnB,oBAAoB;CACrB;AACD;EACE,gBAAgB;EAChB,aAAa;EACb,eAAe;CAChB;AACD;EACE,YAAY;EACZ,gBAAgB;EAChB,mBAAmB;CACpB;AACD;EACE,mBAAmB;EACnB,oBAAoB;CACrB;AACD;IACI,mBAAmB;IACnB,YAAY;IACZ,aAAa;IACb,0BAA0B;IAC1B,oBAAoB;CACvB;AACD;IACI,iBAAiB;IACjB,YAAY;CACf;AACD;IACI,iBAAiB;IACjB,sBAAsB;IACtB,iBAAiB;IACjB,0BAA0B;IAC1B,mBAAmB;IACnB,gBAAgB;IAChB,gBAAgB;IAChB,sBAAsB;IACtB,iBAAiB;CACpB;AACD;IACI,SAAS;CACZ;AACD;IACI,oBAAoB;IACpB,kBAAkB;CACrB;AACD;IACI,QAAQ;CACX;AACD;IACI,mBAAmB;CACtB;AACD;EACE,gBAAgB;EAChB,UAAU;EACV,YAAY;EACZ,eAAe;EACf,oBAAoB;EACpB,mBAAmB;CACpB;AACD;IACI,0BAA0B;IAC1B,oBAAoB;IACpB,iBAAiB;IACjB,aAAa;IACb,mBAAmB;CACtB;AACD;IACI,aAAa;IACb,yBAAyB;IACzB,gBAAgB;IAChB,uBAAuB;IACvB,WAAW;IACX,UAAU;CACb;AACD;IACI,oBAAoB;IACpB,mBAAmB;IACnB,OAAO;IACP,YAAY;IACZ,YAAY;IACZ,kBAAkB;IAClB,cAAc;IACd,mBAAmB;CACtB;AACD;MACM,iBAAiB;MACjB,YAAY;CACjB",file:"index.vue",sourcesContent:["\n.green[data-v-0c573004] {\n  color: #03b349;\n}\n.doing-course[data-v-0c573004] {\n  padding-bottom: 50px;\n}\n.course-info[data-v-0c573004] {\n  line-height: 2;\n  margin: 10px 0;\n  background: #fff;\n  padding: 10px 20px;\n}\n.chart-box[data-v-0c573004] {\n  height: calc(100% - 140px);\n  overflow: hidden;\n  line-height: 2;\n  margin: 10px 0 0 0;\n  padding: 10px 0 0 0;\n}\n.chart-list-scroll[data-v-0c573004] {\n  padding: 0 20px;\n  height: 100%;\n  overflow: auto;\n}\n.chart-empty[data-v-0c573004] {\n  color: #aaa;\n  padding: 80px 0;\n  text-align: center;\n}\n.msg-item[data-v-0c573004] {\n  position: relative;\n  margin-bottom: 14px;\n}\n.msg-item .user-icon[data-v-0c573004] {\n    position: absolute;\n    width: 30px;\n    height: 30px;\n    border: 2px solid #03b349;\n    border-radius: 40px;\n}\n.msg-item .msg-time[data-v-0c573004] {\n    line-height: 1.3;\n    color: #aaa;\n}\n.msg-item .msg-text[data-v-0c573004] {\n    min-height: 26px;\n    display: inline-block;\n    background: #fff;\n    border: 1px solid #03b349;\n    border-radius: 5px;\n    padding: 0 20px;\n    max-width: 100%;\n    word-break: break-all;\n    text-align: left;\n}\n.msg-item.msg-self .user-icon[data-v-0c573004] {\n    right: 0;\n}\n.msg-item.msg-self .msg-content[data-v-0c573004] {\n    padding-right: 50px;\n    text-align: right;\n}\n.msg-item.msg-other .user-icon[data-v-0c573004] {\n    left: 0;\n}\n.msg-item.msg-other .msg-content[data-v-0c573004] {\n    padding-left: 50px;\n}\n.send-msg[data-v-0c573004] {\n  position: fixed;\n  bottom: 0;\n  width: 100%;\n  line-height: 2;\n  background: #fbf9fe;\n  padding: 0 0 6px 0;\n}\n.send-msg .send-box[data-v-0c573004] {\n    border: 1px solid #eaeaea;\n    border-width: 1px 0;\n    background: #fff;\n    height: 40px;\n    position: relative;\n}\n.send-msg input[data-v-0c573004] {\n    height: 40px;\n    width: calc(100% - 80px);\n    font-size: 16px;\n    padding: 0 70px 0 10px;\n    outline: 0;\n    border: 0;\n}\n.send-msg .send-btn[data-v-0c573004] {\n    background: #03b349;\n    position: absolute;\n    top: 0;\n    right: 10px;\n    color: #fff;\n    padding: 0px 10px;\n    margin: 8px 0;\n    border-radius: 5px;\n}\n.send-msg .send-btn.btn-disabled[data-v-0c573004] {\n      background: #eee;\n      color: #aaa;\n}\n"],sourceRoot:""}])}});
//# sourceMappingURL=12.6be207dccb37b59b1915.js.map