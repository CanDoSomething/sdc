webpackJsonp([11],{"5iCJ":function(e,t,n){var s=n("GqUA");"string"==typeof s&&(s=[[e.i,s,""]]),s.locals&&(e.exports=s.locals);n("rjj0")("51beaa06",s,!0,{})},GqUA:function(e,t,n){t=e.exports=n("FZ+f")(!0),t.push([e.i,"\n.green[data-v-87e9a210] {\n  color: #03b349;\n}\n.doing-course[data-v-87e9a210] {\n  padding-bottom: 50px;\n}\n.course-info[data-v-87e9a210] {\n  line-height: 2;\n  margin: 10px 0;\n  background: #fff;\n  padding: 10px 20px;\n}\n.chart-box[data-v-87e9a210] {\n  height: calc(100% - 140px);\n  overflow: hidden;\n  line-height: 2;\n  margin: 10px 0 0 0;\n  padding: 10px 0 0 0;\n}\n.chart-list-scroll[data-v-87e9a210] {\n  padding: 0 20px;\n  height: 100%;\n  overflow: auto;\n}\n.chart-empty[data-v-87e9a210] {\n  color: #aaa;\n  padding: 80px 0;\n  text-align: center;\n}\n.msg-item[data-v-87e9a210] {\n  position: relative;\n  margin-bottom: 14px;\n}\n.msg-item .user-icon[data-v-87e9a210] {\n    position: absolute;\n    width: 30px;\n    height: 30px;\n    border: 2px solid #03b349;\n    border-radius: 40px;\n}\n.msg-item .msg-time[data-v-87e9a210] {\n    line-height: 1.3;\n    color: #aaa;\n}\n.msg-item .msg-text[data-v-87e9a210] {\n    min-height: 26px;\n    display: inline-block;\n    background: #fff;\n    border: 1px solid #03b349;\n    border-radius: 5px;\n    padding: 0 20px;\n    max-width: 100%;\n    word-break: break-all;\n    text-align: left;\n}\n.msg-item.msg-self .user-icon[data-v-87e9a210] {\n    right: 0;\n}\n.msg-item.msg-self .msg-content[data-v-87e9a210] {\n    padding-right: 50px;\n    text-align: right;\n}\n.msg-item.msg-other .user-icon[data-v-87e9a210] {\n    left: 0;\n}\n.msg-item.msg-other .msg-content[data-v-87e9a210] {\n    padding-left: 50px;\n}\n.send-msg[data-v-87e9a210] {\n  position: fixed;\n  bottom: 0;\n  width: 100%;\n  line-height: 2;\n  background: #fbf9fe;\n  padding: 0 0 6px 0;\n}\n.send-msg .send-box[data-v-87e9a210] {\n    border: 1px solid #eaeaea;\n    border-width: 1px 0;\n    background: #fff;\n    height: 40px;\n    position: relative;\n}\n.send-msg input[data-v-87e9a210] {\n    height: 40px;\n    width: calc(100% - 80px);\n    font-size: 16px;\n    padding: 0 70px 0 10px;\n    outline: 0;\n    border: 0;\n}\n.send-msg .send-btn[data-v-87e9a210] {\n    background: #03b349;\n    position: absolute;\n    top: 0;\n    right: 10px;\n    color: #fff;\n    padding: 0px 10px;\n    margin: 8px 0;\n    border-radius: 5px;\n}\n.send-msg .send-btn.btn-disabled[data-v-87e9a210] {\n      background: #eee;\n      color: #aaa;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/chart/index.vue"],names:[],mappings:";AACA;EACE,eAAe;CAChB;AACD;EACE,qBAAqB;CACtB;AACD;EACE,eAAe;EACf,eAAe;EACf,iBAAiB;EACjB,mBAAmB;CACpB;AACD;EACE,2BAA2B;EAC3B,iBAAiB;EACjB,eAAe;EACf,mBAAmB;EACnB,oBAAoB;CACrB;AACD;EACE,gBAAgB;EAChB,aAAa;EACb,eAAe;CAChB;AACD;EACE,YAAY;EACZ,gBAAgB;EAChB,mBAAmB;CACpB;AACD;EACE,mBAAmB;EACnB,oBAAoB;CACrB;AACD;IACI,mBAAmB;IACnB,YAAY;IACZ,aAAa;IACb,0BAA0B;IAC1B,oBAAoB;CACvB;AACD;IACI,iBAAiB;IACjB,YAAY;CACf;AACD;IACI,iBAAiB;IACjB,sBAAsB;IACtB,iBAAiB;IACjB,0BAA0B;IAC1B,mBAAmB;IACnB,gBAAgB;IAChB,gBAAgB;IAChB,sBAAsB;IACtB,iBAAiB;CACpB;AACD;IACI,SAAS;CACZ;AACD;IACI,oBAAoB;IACpB,kBAAkB;CACrB;AACD;IACI,QAAQ;CACX;AACD;IACI,mBAAmB;CACtB;AACD;EACE,gBAAgB;EAChB,UAAU;EACV,YAAY;EACZ,eAAe;EACf,oBAAoB;EACpB,mBAAmB;CACpB;AACD;IACI,0BAA0B;IAC1B,oBAAoB;IACpB,iBAAiB;IACjB,aAAa;IACb,mBAAmB;CACtB;AACD;IACI,aAAa;IACb,yBAAyB;IACzB,gBAAgB;IAChB,uBAAuB;IACvB,WAAW;IACX,UAAU;CACb;AACD;IACI,oBAAoB;IACpB,mBAAmB;IACnB,OAAO;IACP,YAAY;IACZ,YAAY;IACZ,kBAAkB;IAClB,cAAc;IACd,mBAAmB;CACtB;AACD;MACM,iBAAiB;MACjB,YAAY;CACjB",file:"index.vue",sourcesContent:["\n.green[data-v-87e9a210] {\n  color: #03b349;\n}\n.doing-course[data-v-87e9a210] {\n  padding-bottom: 50px;\n}\n.course-info[data-v-87e9a210] {\n  line-height: 2;\n  margin: 10px 0;\n  background: #fff;\n  padding: 10px 20px;\n}\n.chart-box[data-v-87e9a210] {\n  height: calc(100% - 140px);\n  overflow: hidden;\n  line-height: 2;\n  margin: 10px 0 0 0;\n  padding: 10px 0 0 0;\n}\n.chart-list-scroll[data-v-87e9a210] {\n  padding: 0 20px;\n  height: 100%;\n  overflow: auto;\n}\n.chart-empty[data-v-87e9a210] {\n  color: #aaa;\n  padding: 80px 0;\n  text-align: center;\n}\n.msg-item[data-v-87e9a210] {\n  position: relative;\n  margin-bottom: 14px;\n}\n.msg-item .user-icon[data-v-87e9a210] {\n    position: absolute;\n    width: 30px;\n    height: 30px;\n    border: 2px solid #03b349;\n    border-radius: 40px;\n}\n.msg-item .msg-time[data-v-87e9a210] {\n    line-height: 1.3;\n    color: #aaa;\n}\n.msg-item .msg-text[data-v-87e9a210] {\n    min-height: 26px;\n    display: inline-block;\n    background: #fff;\n    border: 1px solid #03b349;\n    border-radius: 5px;\n    padding: 0 20px;\n    max-width: 100%;\n    word-break: break-all;\n    text-align: left;\n}\n.msg-item.msg-self .user-icon[data-v-87e9a210] {\n    right: 0;\n}\n.msg-item.msg-self .msg-content[data-v-87e9a210] {\n    padding-right: 50px;\n    text-align: right;\n}\n.msg-item.msg-other .user-icon[data-v-87e9a210] {\n    left: 0;\n}\n.msg-item.msg-other .msg-content[data-v-87e9a210] {\n    padding-left: 50px;\n}\n.send-msg[data-v-87e9a210] {\n  position: fixed;\n  bottom: 0;\n  width: 100%;\n  line-height: 2;\n  background: #fbf9fe;\n  padding: 0 0 6px 0;\n}\n.send-msg .send-box[data-v-87e9a210] {\n    border: 1px solid #eaeaea;\n    border-width: 1px 0;\n    background: #fff;\n    height: 40px;\n    position: relative;\n}\n.send-msg input[data-v-87e9a210] {\n    height: 40px;\n    width: calc(100% - 80px);\n    font-size: 16px;\n    padding: 0 70px 0 10px;\n    outline: 0;\n    border: 0;\n}\n.send-msg .send-btn[data-v-87e9a210] {\n    background: #03b349;\n    position: absolute;\n    top: 0;\n    right: 10px;\n    color: #fff;\n    padding: 0px 10px;\n    margin: 8px 0;\n    border-radius: 5px;\n}\n.send-msg .send-btn.btn-disabled[data-v-87e9a210] {\n      background: #eee;\n      color: #aaa;\n}\n"],sourceRoot:""}])},Yid5:function(e,t,n){"use strict";function s(e){n("5iCJ")}Object.defineProperty(t,"__esModule",{value:!0});var i=n("vLgD"),a=n("//Fk"),o=n.n(a),r=n("mvHQ"),d=n.n(r),c=n("Dd8w"),u=n.n(c),A=n("NYxO"),g=n("X2Oc"),h={name:"chartMixin",data:function(){return{ws:null,otherImg:"",nowStuCode:"",nowTeaCode:"",stuIsInGroup:!1,teaIsInGroup:!1}},computed:u()({},Object(A.b)({userType:function(e){return e.user.userType},openid:function(e){return e.user.openid},selfImg:function(e){return e.user.infoObj.teaHeadimgurl||e.user.infoObj.stuHeadimgurl}})),mounted:function(){this.getOpenid()},watch:{courseId:function(){this.getOpenid()}},methods:{getOpenid:function(){var e=this;void 0!==this.courseId&&""!==this.courseId&&Object(i.a)({url:"http://www.zhiheyikaoqin.cn/course/getOnClassUserOpenid?courseId="+this.courseId,method:"get",data:{artId:this.$route.params.id,openid:this.$store.state.user.openid}}).then(function(t){0===t.data.code&&(e.nowTeaCode=t.data.data.teaOpenid,e.nowStuCode=t.data.data.stuOpenid,e.initWebsocket())})},initWebsocket:function(){if(null===this.ws&&""!==this.courseId){var e="ws://47.93.225.12:8082/socketServer/"+this.openid;this.ws=new WebSocket(e),this.ws.onopen=this.websocketonopen,this.ws.onmessage=this.websocketonmessage,this.ws.onerror=this.websocketonerror,this.ws.onclose=this.websocketclose}},websocketonopen:function(){this.checkIsIn()},websocketonmessage:function(e){var t=JSON.parse(e.data);this.recSocketMsg(t),console.log(t)},websocketonerror:function(){this.initWebSocket()},websocketsend:function(e){this.ws.send(d()(e))},websocketclose:function(e){console.log("断开连接",e)},checkIsIn:function(){var e={groupname:this.courseId,opttype:"GetChatGroupUserList",username:this.openid};this.websocketsend(e)},teaJoinGroup:function(){var e={addusername:this.nowStuCode,groupname:this.courseId,opttype:"CreateChatGroup",username:this.openid};this.websocketsend(e),this.loading=!1},stuJoinGroup:function(){},getHis:function(){var e={groupname:this.courseId,opttype:"GetChatGroupChatRecordList",username:this.openid};this.websocketsend(e)},sendSocketMsg:function(e){var t={content:e,groupname:this.courseId,opttype:"SendToChatGroup",username:this.openid};this.websocketsend(t)},recSocketMsg:function(e){var t=this;console.log(e),"GetChatGroupUserList"===e.opttype?0!==e.errno?"teacher"===this.userType?this.teaJoinGroup():Object(g.a)(this,"请退出,等待老师开课"):(this.loading=!1,this.getHis()):"GetChatGroupChatRecordList"===e.opttype?(this.loading=!1,0===e.errno?this.renderHis(e.list):Object(g.a)(this,e.errmsg),this.$nextTick(function(){t.loading=!1})):"SendToChatGroup"===e.opttype&&this.renderChart(e)},renderHis:function(e){var t=this;console.log("list",e),e.map(function(e){var n=JSON.parse(e.content);n.time=new Date(e.inserttime),t.renderChart(n)})},renderChart:function(e){var t=this,n=e.username===this.openid?"self":"other";console.log(e,n),""===this[n+"Img"]?new o.a(function(s){t.getInfo(e.username,n,s)}).then(function(){t.sendMsgFn(n,e.content,e.time)}):this.sendMsgFn(n,e.content,e.time)},getInfo:function(e,t,n){"self"===t&&""===this.selfImg&&this._getImg(e,"self",n),"other"===t&&""===this.otherImg&&this._getImg(e,"other",n)},_getImg:function(e,t,n){var s=this;Object(i.a)({url:"http://www.zhiheyikaoqin.cn/user/queryUserInfo?openid="+e,method:"get"}).then(function(e){if(n(),0===Number(e.data.code)){var i=e.data.data;console.log("obj",i,t),s[t+"Img"]=i.stuHeadimgurl||i.teaHeadimgurl}},20)}}},p=n("8pLc"),m=n("KgXo"),l=(p.a,m.a,{name:"chart",components:{noData:p.a,loading:m.a},mixins:[h],data:function(){return{loading:!0,noCourse:!1,courseName:"",teaName:"",courseTime:"",chartList:[],sendMsg:"",courseId:"",doingList:[],count:0,isEnd:!1}},computed:{btnDisabled:function(){return""===this.sendMsg},teaOpenid:function(){return this.$store.state.user.infoObj.teaOpenid||""},stuOpenid:function(){return this.$store.state.user.infoObj.stuOpenid||""},userType:function(){return this.$store.state.user.userType}},mounted:function(){this.$store.commit("SET_RETURN_TEXT","在线交流"),this.$store.commit("SET_RETURN_URL","/my"),this.getDoingList()},watch:{userType:function(){this.getDoingList()}},methods:{getCourseCount:function(){var e=this;Object(i.a)({url:"http://www.zhiheyikaoqin.cn/course/getCountDown?courseId="+this.courseId,method:"get"}).then(function(t){0===t.data.code&&("end"===t.data.data?e.showEnd():(e.count=Number(t.data.data),e.startCount()))})},checkEnd:function(){var e=this;Object(i.a)({url:"http://www.zhiheyikaoqin.cn/course/onCourseEnd?courseId="+this.courseId,method:"get"}).then(function(t){0===t.data.code&&(t.data.data=!0,e.isEnd=t.data.data,t.data.data?e.showEnd():e.startCount())})},startCount:function(){var e=this;setTimeout(function(){e.checkEnd()},1e3*this.count)},showEnd:function(){this.isEnd=!0,Object(g.b)(this,"课程已结束")},clickSend:function(){this.isEnd||(this.sendSocketMsg(this.sendMsg),this.sendMsgFn("self",this.sendMsg))},sendMsgFn:function(e,t,n){if(""!==t){var s=n||new Date;s=s.toTimeString().split(" ")[0],this.chartList.push({time:s,role:e,text:t}),this.$nextTick(function(){document.querySelector(".chart-list-scroll").scrollTop=999999}),"self"===e&&(this.sendMsg="")}},getTime:function(e){return e.split(" ")[1].split(":")[0]+":"+e.split(" ")[1].split(":")[1]},getDoingList:function(){var e=this;this.loading=!0;var t="";"teacher"===this.userType?(t="http://www.zhiheyikaoqin.cn/tea/findTeaHistoryCourse",t+="?teaOpenid="+this.teaOpenid+"&page=1&pageSize=1000"):"student"===this.userType&&(t="http://www.zhiheyikaoqin.cn/stu/lookHistory",t+="?stuOpenid="+this.stuOpenid+"&page=1&pageSize=1000"),Object(i.a)({url:t,method:"get"}).then(function(t){e.loading=!1,0===t.data.code&&void 0!==t.data.data?(e.doingList=t.data.data.filter(function(t){return"teacher"===e.userType?302===t.teaCourse.courseStatus:302===t.teaCourse.courseStatus&&401===t.subStatus}).map(function(t){return t.teaCourse.courseTime=t.teaCourse.courseDate.split(" ")[0]+" "+e.getTime(t.teaCourse.courseStartTime)+" - "+e.getTime(t.teaCourse.courseEndTime),t}),0===e.doingList.length?(e.noCourse=!0,Object(g.a)(e,"请退出等待开课")):(e.courseId=e.doingList[0].teaCourse.courseId,e.courseName=e.doingList[0].teaCourse.courseName,e.courseTime=e.doingList[0].teaCourse.courseTime,"student"===e.userType?e.teaName=e.doingList[0].teaName:e.teaName=e.doingList[0].teaBase.teaName,e.noCourse=!1,e.getCourseCount())):(e.noCourse=!0,Object(g.a)(e,"请退出等待开课"))})}}}),C=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"doing-course"},[!e.loading&&e.noCourse?n("div",{staticClass:"no-course"},[n("no-data",{attrs:{text:"当前没有正在上的课"}})],1):e._e(),e._v(" "),e.loading||e.noCourse?e._e():n("div",{staticClass:"course-info"},[n("div",{staticClass:"course-name"},[n("label",[e._v("课程名称：")]),e._v(" "),n("span",[e._v(e._s(e.courseName))])]),e._v(" "),n("div",{staticClass:"course-time"},[n("label",[e._v("课程时间：")]),e._v(" "),n("span",[e._v(e._s(e.courseTime))])]),e._v(" "),n("div",{staticClass:"course-time"},[n("label",[e._v("老师姓名：")]),e._v(" "),n("span",[e._v(e._s(e.teaName))])])]),e._v(" "),e.loading||e.noCourse?e._e():n("div",{staticClass:"chart-box"},[n("div",{staticClass:"chart-list-scroll"},[e._l(e.chartList,function(t){return 0!==e.chartList.length?n("div",{staticClass:"chart-list"},["self"===t.role?n("div",{staticClass:"msg-self msg-item"},[n("img",{staticClass:"user-icon",attrs:{src:e.selfImg}}),e._v(" "),n("div",{staticClass:"msg-content"},[n("div",{staticClass:"msg-text"},[e._v(e._s(t.text))]),e._v(" "),n("div",{staticClass:"msg-time"},[e._v(e._s(t.time))])])]):e._e(),e._v(" "),"other"===t.role?n("div",{staticClass:"msg-other msg-item"},[n("img",{staticClass:"user-icon",attrs:{src:e.otherImg}}),e._v(" "),n("div",{staticClass:"msg-content"},[n("div",{staticClass:"msg-text"},[e._v(e._s(t.text))]),e._v(" "),n("div",{staticClass:"msg-time"},[e._v(e._s(t.time))])])]):e._e()]):e._e()}),e._v(" "),0===e.chartList.length?n("div",{staticClass:"chart-list chart-empty"},[e._v("\n        暂无聊天记录\n      ")]):e._e()],2)]),e._v(" "),e.loading||e.noCourse?e._e():n("div",{staticClass:"send-msg"},[n("div",{staticClass:"send-box"},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.sendMsg,expression:"sendMsg"}],attrs:{type:"text",disabled:e.isEnd},domProps:{value:e.sendMsg},on:{keyup:function(t){return"button"in t||!e._k(t.keyCode,"enter",13,t.key,"Enter")?e.clickSend(t):null},input:function(t){t.target.composing||(e.sendMsg=t.target.value)}}}),e._v(" "),n("div",{staticClass:"send-btn",class:{"btn-disabled":e.isEnd||e.btnDisabled},on:{click:function(t){return t.stopPropagation(),e.clickSend(t)}}},[e._v("发送")])])]),e._v(" "),e.loading?[n("loading")]:e._e()],2)},f=[],v={render:C,staticRenderFns:f},B=v,b=n("VU/8"),x=s,I=b(l,B,!1,x,"data-v-87e9a210",null);t.default=I.exports}});
//# sourceMappingURL=11.f7630c7209d10df8cead.js.map