webpackJsonp([5],{"5vhn":function(t,e,s){e=t.exports=s("FZ+f")(!0),e.push([t.i,"\n.green[data-v-68a7cb16] {\n  color: #03b349;\n}\n.user-info-we[data-v-68a7cb16] {\n  padding: 30px 0 0 0;\n  text-align: center;\n}\n.we-img[data-v-68a7cb16] {\n  width: 80px;\n  height: 80px;\n  display: inline-block;\n}\n.we-img img[data-v-68a7cb16] {\n    width: 100%;\n    height: 100%;\n    border-radius: 100%;\n    border: 3px solid #03b349;\n}\n.we-name[data-v-68a7cb16] {\n  padding: 10px 40px;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/my/userInfo.vue"],names:[],mappings:";AACA;EACE,eAAe;CAChB;AACD;EACE,oBAAoB;EACpB,mBAAmB;CACpB;AACD;EACE,YAAY;EACZ,aAAa;EACb,sBAAsB;CACvB;AACD;IACI,YAAY;IACZ,aAAa;IACb,oBAAoB;IACpB,0BAA0B;CAC7B;AACD;EACE,mBAAmB;CACpB",file:"userInfo.vue",sourcesContent:["\n.green[data-v-68a7cb16] {\n  color: #03b349;\n}\n.user-info-we[data-v-68a7cb16] {\n  padding: 30px 0 0 0;\n  text-align: center;\n}\n.we-img[data-v-68a7cb16] {\n  width: 80px;\n  height: 80px;\n  display: inline-block;\n}\n.we-img img[data-v-68a7cb16] {\n    width: 100%;\n    height: 100%;\n    border-radius: 100%;\n    border: 3px solid #03b349;\n}\n.we-name[data-v-68a7cb16] {\n  padding: 10px 40px;\n}\n"],sourceRoot:""}])},DGz0:function(t,e,s){"use strict";function n(t){s("LBIE")}var i=s("vLgD"),a=s("X2Oc"),o=s("XmB9"),r=(o.a,String,Object,{name:"teaReg",mixins:[o.a],data:function(){return{form:{teaCode:"",teaName:"",teaSubject:"",teaPasswd:""}}},props:{type:{type:String,default:"reg"},info:Object},mounted:function(){"reg"!==this.type&&void 0!==this.info&&(this.form.teaCode=this.info.teaCode||"",this.form.teaName=this.info.teaName||"",this.form.teaSubject=this.info.teaSubject||"")},methods:{reg:function(){var t=this;if(this.isValid=!1,this.checkValid("teaCode"),this.checkValid("teaName"),this.checkValid("teaSubject"),this.checkValid("teaPasswd"),this.isValid)return void this.$vux.toast.show({text:"信息不能为空",type:"cancel",width:"140px"});Object(i.a)({url:"http://www.zhiheyikaoqin.cn/user/registerTeaBaseByOpenid",method:"post",data:{teaOpenid:this.openid,teaCode:this.form.teaCode,teaName:this.form.teaName,teaSubject:this.form.teaSubject,teaPasswd:this.form.teaPasswd}}).then(function(e){0!==e.data.code?Object(a.b)(t,e.data.msg,"120px"):(Object(a.b)(t,"提交成功"),t.$store.commit("SET_INFO_OBJ",e.data.data),t.$store.commit("SET_REG_STATUS",!0),setTimeout(function(){t.$router.push({name:"course"})},500))})}}}),u=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"register-form tea"},[s("group",[s("x-input",{attrs:{title:"姓名"},model:{value:t.form.teaName,callback:function(e){t.$set(t.form,"teaName",e)},expression:"form.teaName"}}),t._v(" "),s("x-input",{attrs:{title:"工号"},model:{value:t.form.teaCode,callback:function(e){t.$set(t.form,"teaCode",e)},expression:"form.teaCode"}}),t._v(" "),s("x-input",{attrs:{title:"科目"},model:{value:t.form.teaSubject,callback:function(e){t.$set(t.form,"teaSubject",e)},expression:"form.teaSubject"}})],1),t._v(" "),s("group",["reg"===t.type?s("div",{staticClass:"password-control vux-cell-box"},[t.pwdVis?t._e():s("x-input",{attrs:{title:"密码",type:"password"},model:{value:t.form.teaPasswd,callback:function(e){t.$set(t.form,"teaPasswd",e)},expression:"form.teaPasswd"}}),t._v(" "),t.pwdVis?s("x-input",{attrs:{title:"密码",type:"text"},model:{value:t.form.teaPasswd,callback:function(e){t.$set(t.form,"teaPasswd",e)},expression:"form.teaPasswd"}}):t._e(),t._v(" "),t.pwdVis?t._e():s("i",{staticClass:"iconfont icon-mimakejian",on:{click:function(e){t.pwdVisible(!0)}}}),t._v(" "),t.pwdVis?s("i",{staticClass:"iconfont icon-bukejian",on:{click:function(e){t.pwdVisible(!1)}}}):t._e()],1):t._e()]),t._v(" "),"reg"===t.type?s("div",{staticClass:"btn send-btn",on:{click:t.reg}},[t._v("提交")]):t._e()],1)},c=[],d={render:u,staticRenderFns:c},l=d,p=s("VU/8"),f=n,m=p(r,l,!1,f,"data-v-c8bdbf5e",null);e.a=m.exports},Ewo0:function(t,e,s){var n=s("5vhn");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);s("rjj0")("1329246c",n,!0,{})},JFmw:function(t,e,s){e=t.exports=s("FZ+f")(!0),e.push([t.i,"\n.password-control[data-v-c8bdbf5e] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-c8bdbf5e] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/register/teaReg.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,oBAAoB;CACrB;AACD;IACI,mBAAmB;IACnB,SAAS;IACT,OAAO;IACP,aAAa;IACb,kBAAkB;CACrB",file:"teaReg.vue",sourcesContent:["\n.password-control[data-v-c8bdbf5e] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-c8bdbf5e] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n"],sourceRoot:""}])},LBIE:function(t,e,s){var n=s("JFmw");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);s("rjj0")("d3ab78d0",n,!0,{})},"WS+U":function(t,e,s){e=t.exports=s("FZ+f")(!0),e.push([t.i,"\n.password-control[data-v-2c21e37e] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-2c21e37e] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n","",{version:3,sources:["/Users/lh/work/zk/fe/src/register/stuReg.vue"],names:[],mappings:";AACA;EACE,mBAAmB;EACnB,oBAAoB;CACrB;AACD;IACI,mBAAmB;IACnB,SAAS;IACT,OAAO;IACP,aAAa;IACb,kBAAkB;CACrB",file:"stuReg.vue",sourcesContent:["\n.password-control[data-v-2c21e37e] {\n  position: relative;\n  padding-right: 40px;\n}\n.password-control .iconfont[data-v-2c21e37e] {\n    position: absolute;\n    right: 0;\n    top: 0;\n    height: 22px;\n    padding: 7px 12px;\n}\n"],sourceRoot:""}])},XmB9:function(t,e,s){"use strict";var n=s("rHil"),i=s("pDNl"),a=s("7Ah8");e.a={components:{Group:n.a,XInput:i.a,PopupPicker:a.a},data:function(){return{pwdVis:!0,isValid:!1}},computed:{openid:function(){return this.$store.state.user.openid}},methods:{pwdVisible:function(t){this.pwdVis=t},checkValid:function(t){this.isValid=this.isValid||""===this.form[t].trim()}}}},l6sf:function(t,e,s){var n=s("WS+U");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);s("rjj0")("b35b039e",n,!0,{})},t01H:function(t,e,s){"use strict";function n(t){s("l6sf")}var i=s("vLgD"),a=s("X2Oc"),o=s("XmB9"),r=(o.a,String,Object,{name:"stuReg",mixins:[o.a],data:function(){return{form:{stuCode:"",stuName:"",stuLevel:"",stuGrade:"",stuClass:"",stuPasswd:""},grades:["高中","初中"],levels:["2016级","2017级","2018级","2019级"],classes:["1班","2班","3班","4班","5班","6班","7班","8班","9班","10班","11班","12班","13班","14班","15班","16班","17班","18班","19班"]}},props:{type:{type:String,default:"reg"},info:Object},mounted:function(){"reg"!==this.type&&void 0!==this.info&&(this.form.stuCode=this.info.stuCode||"",this.form.stuName=this.info.stuName||"",this.form.stuLevel=this.info.stuLevel||"",this.form.stuGrade=this.info.stuGrade||"",this.form.stuClass=this.info.stuClass||"")},methods:{selGrade:function(t){console.log(t),this.form.stuGrade=t[0]},selLevel:function(t){console.log(t),this.form.stuLevel=t[0]},selClass:function(t){console.log(t),this.form.stuClass=t[0]},reg:function(){var t=this;if(this.isValid=!1,this.checkValid("stuCode"),this.checkValid("stuName"),this.checkValid("stuLevel"),this.checkValid("stuGrade"),this.checkValid("stuClass"),this.checkValid("stuPasswd"),this.isValid)return void this.$vux.toast.show({text:"信息不能为空",type:"cancel",width:"140px"});Object(i.a)({url:"http://www.zhiheyikaoqin.cn/user/registerStuBaseByOpenid",method:"post",data:{stuOpenid:this.openid,stuCode:this.form.stuCode,stuName:this.form.stuName,stuLevel:this.form.stuLevel,stuGrade:this.form.stuGrade,stuClass:this.form.stuClass,stuPasswd:this.form.stuPasswd}}).then(function(e){0!==e.data.code?Object(a.b)(t,e.data.msg,"120px"):(Object(a.b)(t,"提交成功"),t.$store.commit("SET_INFO_OBJ",e.data.data),t.$store.commit("SET_REG_STATUS",!0),setTimeout(function(){t.$router.push({name:"course"})},500))})}}}),u=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"register-form stu"},[s("group",[s("x-input",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"姓名"},model:{value:t.form.stuName,callback:function(e){t.$set(t.form,"stuName",e)},expression:"form.stuName"}}),t._v(" "),s("x-input",{attrs:{readonly:"reg"!==t.type,title:"学号"},model:{value:t.form.stuCode,callback:function(e){t.$set(t.form,"stuCode",e)},expression:"form.stuCode"}}),t._v(" "),s("popup-picker",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"年级",value:[t.form.stuLevel],data:t.levels,columns:1,"value-text-align":"left"},on:{"on-change":t.selLevel}}),t._v(" "),s("popup-picker",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"分段",value:[t.form.stuGrade],data:t.grades,columns:1,"value-text-align":"left"},on:{"on-change":t.selGrade}}),t._v(" "),s("popup-picker",{attrs:{readonly:"reg"!==t.type,disabled:"reg"!==t.type,title:"班级",value:[t.form.stuClass],data:t.classes,columns:1,"value-text-align":"left"},on:{"on-change":t.selClass}})],1),t._v(" "),s("group",["reg"===t.type?s("div",{staticClass:"password-control vux-cell-box"},[t.pwdVis?t._e():s("x-input",{attrs:{title:"密码",type:"password"},model:{value:t.form.stuPasswd,callback:function(e){t.$set(t.form,"stuPasswd",e)},expression:"form.stuPasswd"}}),t._v(" "),t.pwdVis?s("x-input",{attrs:{title:"密码",type:"text"},model:{value:t.form.stuPasswd,callback:function(e){t.$set(t.form,"stuPasswd",e)},expression:"form.stuPasswd"}}):t._e(),t._v(" "),t.pwdVis?t._e():s("i",{staticClass:"iconfont icon-mimakejian",on:{click:function(e){t.pwdVisible(!0)}}}),t._v(" "),t.pwdVis?s("i",{staticClass:"iconfont icon-bukejian",on:{click:function(e){t.pwdVisible(!1)}}}):t._e()],1):t._e()]),t._v(" "),"reg"===t.type?s("div",{staticClass:"btn send-btn",on:{click:t.reg}},[t._v("提交")]):t._e()],1)},c=[],d={render:u,staticRenderFns:c},l=d,p=s("VU/8"),f=n,m=p(r,l,!1,f,"data-v-2c21e37e",null);e.a=m.exports},zuZW:function(t,e,s){"use strict";function n(t){s("Ewo0")}Object.defineProperty(e,"__esModule",{value:!0});var i=s("Dd8w"),a=s.n(i),o=s("NYxO"),r=s("t01H"),u=s("DGz0"),c=s("vLgD"),d=s("X2Oc"),l=(r.a,u.a,a()({},Object(o.b)({stuOpenid:function(t){return t.user.infoObj.stuOpenid},teaOpenid:function(t){return t.user.infoObj.teaOpenid},infoObj:function(t){return t.user.infoObj},userType:function(t){return t.user.userType}}),{weName:function(){return this.$store.state.user.infoObj.stuNickname||this.$store.state.user.infoObj.teaNickname},weImg:function(){return this.$store.state.user.infoObj.stuHeadimgurl||this.$store.state.user.infoObj.teaHeadimgurl},type:function(){var t=this.$store.state.user.userType;return this.$store.commit("SET_RETURN_TEXT","我的信息"),t}}),{name:"userInfo",components:{stuReg:r.a,teaReg:u.a},data:function(){return{}},computed:a()({},Object(o.b)({stuOpenid:function(t){return t.user.infoObj.stuOpenid},teaOpenid:function(t){return t.user.infoObj.teaOpenid},infoObj:function(t){return t.user.infoObj},userType:function(t){return t.user.userType}}),{weName:function(){return this.$store.state.user.infoObj.stuNickname||this.$store.state.user.infoObj.teaNickname},weImg:function(){return this.$store.state.user.infoObj.stuHeadimgurl||this.$store.state.user.infoObj.teaHeadimgurl},type:function(){var t=this.$store.state.user.userType;return this.$store.commit("SET_RETURN_TEXT","我的信息"),t}}),mounted:function(){},methods:{delUser:function(){var t=this;this.$vux.confirm.show({content:"是否注销用户",onCancel:function(){console.log(this),console.log(t)},onConfirm:function(){t._delAPI()}})},_delAPI:function(){var t=this,e="",s={};e="teacher"===this.userType?"http://www.zhiheyikaoqin.cn/user/deleteTeaByOpenid?teaOpenid="+this.teaOpenid:"http://www.zhiheyikaoqin.cn/user/deleteStuByOpenid?stuOpenid="+this.stuOpenid,console.log(e,s),Object(c.a)({url:e,method:"get"}).then(function(e){0===e.data.code?(Object(d.b)(t,"注销成功"),setTimeout(function(){t.$router.push({name:"selRegType"})},500)):Object(d.a)(t,e.data.msg)})}}}),p=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"user-info"},[s("div",{staticClass:"user-info-we"},[s("div",{staticClass:"we-img"},[s("img",{attrs:{src:t.weImg}})]),t._v(" "),s("div",{staticClass:"we-name"},[t._v("微信昵称： "+t._s(t.weName))])]),t._v(" "),"student"===t.type?s("stu-reg",{attrs:{info:t.infoObj,type:"info"}}):t._e(),t._v(" "),"teacher"===t.type?s("tea-reg",{attrs:{info:t.infoObj,type:"info"}}):t._e(),t._v(" "),s("div",{staticClass:"btn send-btn del-user",on:{click:t.delUser}},[t._v("注销用户")])],1)},f=[],m={render:p,staticRenderFns:f},h=m,v=s("VU/8"),b=n,g=v(l,h,!1,b,"data-v-68a7cb16",null);e.default=g.exports}});
//# sourceMappingURL=5.aabc431c7481c9fea96e.js.map