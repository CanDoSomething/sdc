webpackJsonp([15],{"/kga":function(t,e,n){"use strict";function i(t){n("ZdaW")}var o=n("JkZY"),s=(o.a,Boolean,String,String,Number,String,String,Boolean,Object,Boolean,{mixins:[o.a],name:"x-dialog",model:{prop:"show",event:"change"},props:{show:{type:Boolean,default:!1},maskTransition:{type:String,default:"vux-mask"},maskZIndex:[String,Number],dialogTransition:{type:String,default:"vux-dialog"},dialogClass:{type:String,default:"weui-dialog"},hideOnBlur:Boolean,dialogStyle:Object,scroll:{type:Boolean,default:!0,validator:function(t){return!0}}},computed:{maskStyle:function(){if(void 0!==this.maskZIndex)return{zIndex:this.maskZIndex}}},mounted:function(){"undefined"!=typeof window&&window.VUX_CONFIG&&"VIEW_BOX"===window.VUX_CONFIG.$layout&&(this.layout="VIEW_BOX")},watch:{show:function(t){this.$emit("update:show",t),this.$emit(t?"on-show":"on-hide"),t?this.addModalClassName():this.removeModalClassName()}},methods:{shouldPreventScroll:function(){var t=/iPad|iPhone|iPod/i.test(window.navigator.userAgent),e=this.$el.querySelector("input")||this.$el.querySelector("textarea");if(t&&e)return!0},hide:function(){this.hideOnBlur&&(this.$emit("update:show",!1),this.$emit("change",!1),this.$emit("on-click-mask"))}},data:function(){return{layout:""}}}),a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"vux-x-dialog",class:{"vux-x-dialog-absolute":"VIEW_BOX"===t.layout}},[n("transition",{attrs:{name:t.maskTransition}},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],staticClass:"weui-mask",style:t.maskStyle,on:{click:t.hide}})]),t._v(" "),n("transition",{attrs:{name:t.dialogTransition}},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],class:t.dialogClass,style:t.dialogStyle},[t._t("default")],2)])],1)},r=[],u={render:a,staticRenderFns:r},l=u,c=n("VU/8"),d=i,m=c(s,l,!1,d,null,null);e.a=m.exports},"2jIZ":function(t,e){},"62KO":function(t,e,n){"use strict";function i(t){n("HsVS")}var o=n("/kga"),s=(o.a,Boolean,Boolean,String,String,Boolean,String,String,String,String,Number,String,String,String,Boolean,Object,Boolean,String,Boolean,Boolean,{name:"confirm",components:{XDialog:o.a},props:{value:{type:Boolean,default:!1},showInput:{type:Boolean,default:!1},placeholder:{type:String,default:""},theme:{type:String,default:"ios"},hideOnBlur:{type:Boolean,default:!1},title:String,confirmText:String,cancelText:String,maskTransition:{type:String,default:"vux-fade"},maskZIndex:[Number,String],dialogTransition:{type:String,default:"vux-dialog"},content:String,closeOnConfirm:{type:Boolean,default:!0},inputAttrs:Object,showContent:{type:Boolean,default:!0},confirmType:{type:String,default:"primary"},showCancelButton:{type:Boolean,default:!0},showConfirmButton:{type:Boolean,default:!0}},created:function(){this.showValue=this.show,this.value&&(this.showValue=this.value)},watch:{value:function(t){this.showValue=t},showValue:function(t){var e=this;this.$emit("input",t),t&&(this.showInput&&(this.msg="",setTimeout(function(){e.$refs.input&&e.setInputFocus()},300)),this.$emit("on-show"))}},data:function(){return{msg:"",showValue:!1}},methods:{getInputAttrs:function(){return this.inputAttrs||{type:"text"}},setInputValue:function(t){this.msg=t},setInputFocus:function(t){t&&t.preventDefault(),this.$refs.input.focus()},_onConfirm:function(){this.showValue&&(this.closeOnConfirm&&(this.showValue=!1),this.$emit("on-confirm",this.msg))},_onCancel:function(){this.showValue&&(this.showValue=!1,this.$emit("on-cancel"))}}}),a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"vux-confirm"},[n("x-dialog",{attrs:{"dialog-class":"android"===t.theme?"weui-dialog weui-skin_android":"weui-dialog","mask-transition":t.maskTransition,"dialog-transition":"android"===t.theme?"vux-fade":t.dialogTransition,"hide-on-blur":t.hideOnBlur,"mask-z-index":t.maskZIndex},on:{"on-hide":function(e){t.$emit("on-hide")}},model:{value:t.showValue,callback:function(e){t.showValue=e},expression:"showValue"}},[t.title?n("div",{staticClass:"weui-dialog__hd",class:{"with-no-content":!t.showContent}},[n("strong",{staticClass:"weui-dialog__title"},[t._v(t._s(t.title))])]):t._e(),t._v(" "),t.showContent?[t.showInput?n("div",{staticClass:"vux-prompt"},["checkbox"===t.getInputAttrs().type?n("input",t._b({directives:[{name:"model",rawName:"v-model",value:t.msg,expression:"msg"}],ref:"input",staticClass:"vux-prompt-msgbox",attrs:{placeholder:t.placeholder,type:"checkbox"},domProps:{checked:Array.isArray(t.msg)?t._i(t.msg,null)>-1:t.msg},on:{touchend:t.setInputFocus,change:function(e){var n=t.msg,i=e.target,o=!!i.checked;if(Array.isArray(n)){var s=t._i(n,null);i.checked?s<0&&(t.msg=n.concat([null])):s>-1&&(t.msg=n.slice(0,s).concat(n.slice(s+1)))}else t.msg=o}}},"input",t.getInputAttrs(),!1)):"radio"===t.getInputAttrs().type?n("input",t._b({directives:[{name:"model",rawName:"v-model",value:t.msg,expression:"msg"}],ref:"input",staticClass:"vux-prompt-msgbox",attrs:{placeholder:t.placeholder,type:"radio"},domProps:{checked:t._q(t.msg,null)},on:{touchend:t.setInputFocus,change:function(e){t.msg=null}}},"input",t.getInputAttrs(),!1)):n("input",t._b({directives:[{name:"model",rawName:"v-model",value:t.msg,expression:"msg"}],ref:"input",staticClass:"vux-prompt-msgbox",attrs:{placeholder:t.placeholder,type:t.getInputAttrs().type},domProps:{value:t.msg},on:{touchend:t.setInputFocus,input:function(e){e.target.composing||(t.msg=e.target.value)}}},"input",t.getInputAttrs(),!1))]):n("div",{staticClass:"weui-dialog__bd"},[t._t("default",[n("div",{domProps:{innerHTML:t._s(t.content)}})])],2)]:t._e(),t._v(" "),n("div",{staticClass:"weui-dialog__ft"},[t.showCancelButton?n("a",{staticClass:"weui-dialog__btn weui-dialog__btn_default",attrs:{href:"javascript:;"},on:{click:t._onCancel}},[t._v(t._s(t.cancelText||"取消"))]):t._e(),t._v(" "),t.showConfirmButton?n("a",{staticClass:"weui-dialog__btn",class:"weui-dialog__btn_"+t.confirmType,attrs:{href:"javascript:;"},on:{click:t._onConfirm}},[t._v(t._s(t.confirmText||"确定"))]):t._e()])],2)],1)},r=[],u={render:a,staticRenderFns:r},l=u,c=n("VU/8"),d=i,m=c(s,l,!1,d,null,null);e.a=m.exports},Bfwr:function(t,e,n){"use strict";function i(t){n("Y4Lg")}var o=(Boolean,String,String,String,{name:"loading",model:{prop:"show",event:"change"},props:{show:Boolean,text:String,position:String,transition:{type:String,default:"vux-mask"}},watch:{show:function(t){this.$emit("update:show",t)}}}),s=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("transition",{attrs:{name:t.transition}},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],staticClass:"weui-loading_toast vux-loading",class:t.text?"":"vux-loading-no-text"},[n("div",{staticClass:"weui-mask_transparent"}),t._v(" "),n("div",{staticClass:"weui-toast",style:{position:t.position}},[n("i",{staticClass:"weui-loading weui-icon_toast"}),t._v(" "),t.text?n("p",{staticClass:"weui-toast__content"},[t._v(t._s(t.text||"加载中")),t._t("default")],2):t._e()])])])},a=[],r={render:s,staticRenderFns:a},u=r,l=n("VU/8"),c=i,d=l(o,u,!1,c,null,null);e.a=d.exports},HoL6:function(t,e){},HsVS:function(t,e){},NHnr:function(t,e,n){"use strict";function i(t){n("2jIZ")}function o(t){n("QKnK")}function s(t){n("HoL6")}function a(t){n("PTvS")}function r(t){n("sk88")}Object.defineProperty(e,"__esModule",{value:!0});var u=n("7+uW"),l=n("v5o6"),c=n.n(l),d=n("/ocq"),m={data:function(){return{menu:[{link:"course",value:"course",name:"找课程",icon:"icon-fs-course"},{link:"tech",value:"tech",name:"看新闻",icon:"icon-xinwen"},{link:"my",value:"my",name:"我的",icon:"icon-wode"}]}},computed:{curMenu:function(){return this.$route.name},infoObj:function(){return this.$store.state.user.infoObj}},methods:{changeMenu:function(t){if(console.log("link",t),"my"===t&&(null===this.infoObj||void 0===this.infoObj.teaOpenid&&void 0===this.infoObj.stuOpenid||""===this.$store.state.user.openid))return void this.$router.push({path:"./selRegType"});this.$router.push({path:"./"+t})}}},h=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"navbar weui-tabbar"},t._l(t.menu,function(e){return n("div",{directives:[{name:"tap",rawName:"v-tap",value:[t.changeMenu,e.link],expression:"[changeMenu, item.link]"}],key:e.value,class:[{active:e.value===t.curMenu},"weui-tabbar__item"]},[n("i",{class:["iconfont",e.icon,"weui-tabbar__icon"]}),t._v(" "),n("p",{staticClass:"weui-tabbar__label"},[t._v(t._s(e.name))])])}),0)},p=[],f={render:h,staticRenderFns:p},v=f,g=n("VU/8"),w=i,_=g(m,v,!1,w,null,null),S=_.exports,T={components:{navbar:S},mounted:function(){this.$store.commit("SET_RETURN_TEXT",""),this.$store.commit("SET_RETURN_URL","")}},b=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-main weui-tab"},[n("div",{staticClass:"weui-tab__panel"},[n("router-view")],1),t._v(" "),n("navbar")],1)},y=[],x={render:b,staticRenderFns:y},C=x,E=n("VU/8"),k=o,R=E(T,C,!1,k,null,null),I=R.exports,O={mounted:function(){this.$store.commit("SET_RETURN_TEXT",""),this.$store.commit("SET_RETURN_URL","")}},N=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-main"},[n("router-view")],1)},$=[],B={render:N,staticRenderFns:$},U=B,P=n("VU/8"),A=s,V=P(O,U,!1,A,null,null),F=V.exports,M={computed:{returnText:function(){return this.$store.state.user.returnText},toUrl:function(){return this.$store.state.user.returnUrl}},methods:{returnBack:function(){""!==this.toUrl?this.$router.push({path:this.toUrl}):this.$router.go(-1)}}},j=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-main"},[n("div",{staticClass:"return-block"},[n("i",{directives:[{name:"tap",rawName:"v-tap",value:[t.returnBack],expression:"[returnBack]"}],staticClass:"iconfont icon-jiantou2"}),t._v(" "),n("span",{directives:[{name:"tap",rawName:"v-tap",value:[t.returnBack],expression:"[returnBack]"}],staticStyle:{"font-size":"15px","margin-left":"-4px","padding-left":"4px"}},[t._v(t._s(t.returnText))])]),t._v(" "),n("router-view",{staticClass:"return-body"})],1)},D=[],H={render:j,staticRenderFns:D},L=H,G=n("VU/8"),q=a,X=G(M,L,!1,q,"data-v-106a29ea",null),Z=X.exports;u.a.use(d.a);var W=function(){return Promise.all([n.e(0),n.e(6)]).then(n.bind(null,"LP/k"))},Y=function(){return Promise.all([n.e(0),n.e(11)]).then(n.bind(null,"ZCNk"))},K=function(){return Promise.all([n.e(0),n.e(7)]).then(n.bind(null,"LrCi"))},z=function(){return Promise.all([n.e(0),n.e(1)]).then(n.bind(null,"nAsw"))},J=function(){return Promise.all([n.e(0),n.e(9)]).then(n.bind(null,"ba/d"))},Q=function(){return Promise.all([n.e(0),n.e(13)]).then(n.bind(null,"3hTT"))},tt=function(){return Promise.all([n.e(0),n.e(4)]).then(n.bind(null,"6Mp4"))},et=function(){return Promise.all([n.e(0),n.e(5)]).then(n.bind(null,"p99J"))},nt=function(){return Promise.all([n.e(0),n.e(12)]).then(n.bind(null,"Yid5"))},it=function(){return Promise.all([n.e(0),n.e(8)]).then(n.bind(null,"Kr5b"))},ot=function(){return Promise.all([n.e(0),n.e(2)]).then(n.bind(null,"xeHp"))},st=function(){return Promise.all([n.e(0),n.e(10)]).then(n.bind(null,"IhT5"))},at=function(){return Promise.all([n.e(0),n.e(3)]).then(n.bind(null,"zuZW"))},rt=new d.a({routes:[{path:"/",redirect:"/course"},{path:"/course",component:I,name:"course0",meta:{tab:"index",title:"index"},children:[{path:"/course",name:"course",component:W,meta:{title:"course"}}]},{path:"/tech",component:I,name:"tech0",meta:{tab:"tech",title:"tech"},children:[{path:"/tech",name:"tech",component:Y,meta:{title:"tech"}}]},{path:"/newsDetail",component:Z,name:"newsDetail0",meta:{tab:"newsDetail0",title:"newsDetail0"},children:[{path:"/news/:id",name:"newsDetail",component:K,meta:{title:"newsDetail"}}]},{path:"/my",component:I,name:"my0",meta:{tab:"my",title:"my"},children:[{path:"/my",name:"my",component:z,meta:{title:"my"}}]},{path:"/feedback",component:Z,name:"feedback0",meta:{tab:"feedback",title:"feedback"},children:[{path:"/feedback",name:"feedback",component:et,meta:{title:"feedback"}}]},{path:"/bookCourse",component:Z,name:"bookCourse0",meta:{tab:"bookCourse",title:"bookCourse"},children:[{path:"/bookCourse/:type",name:"bookCourse",component:J,meta:{title:"bookCourse"}},{path:"/cancelCourse",name:"cancelCourse",component:Q,meta:{title:"cancelCourse"}}]},{path:"/createCourse",component:Z,name:"createCourse0",meta:{tab:"bookCourse",title:"createCourse"},children:[{path:"/createCourse",name:"createCourse",component:tt,meta:{title:"createCourse"}}]},{path:"/chart",component:Z,name:"chart0",meta:{tab:"chart",title:"chart"},children:[{path:"/chart",name:"chart",component:nt,meta:{title:"chart"}}]},{path:"/viewCourseStu",component:Z,name:"viewCourseStu0",meta:{tab:"viewCourseStu",title:"viewCourseStu"},children:[{path:"/viewCourseStu",name:"viewCourseStu",component:it,meta:{title:"viewCourseStu"}}]},{path:"/register",component:Z,name:"register0",meta:{tab:"register",title:"register"},children:[{path:"/register",name:"register",component:ot,meta:{title:"register"}},{path:"/userInfo",name:"userInfo",component:at,meta:{title:"userInfo"}}]},{path:"/selRegType",component:F,name:"selRegType0",meta:{tab:"selRegType",title:"selRegType"},children:[{path:"/selRegType",name:"selRegType",component:st,meta:{title:"selRegType"}}]}]}),ut=n("NYxO"),lt=n("vLgD"),ct={state:{userType:"",showMsg:!1,msgType:"",msg:"",returnText:"",returnUrl:"",baseUrl:"http://www.zhiheyikaoqin.cn/sdc/sdc/",openid:"",isRegAuth:!1,isReg:!0,hasInfo:!1,infoObj:{}},mutations:{SET_OPENID:function(t,e){console.log("dd",e),t.openid=e},SHOW_TOAST:function(t,e){t.msg=e,t.msgType="",t.showMsg=!0},SHOW_ERROR_TOAST:function(t,e){t.msg=e,t.msgType="error",t.showMsg=!0},HIDE_TOAST:function(t){t.msg="",t.msgType="",t.showMsg=!1},SET_RETURN_TEXT:function(t,e){t.returnText=e},SET_RETURN_URL:function(t,e){t.returnUrl=e},SET_USR_TYPE:function(t,e){t.userType=e},SET_REG_AUTH:function(t,e){t.isRegAuth=e},SET_REG_STATUS:function(t,e){t.isReg=e},SET_REG_INFO:function(t,e){t.hasInfo=e},SET_INFO_OBJ:function(t,e){t.infoObj=e}},actions:{getInfo:function(t){var e=this,n=t.commit;Object(lt.a)({url:"user/queryUserInfo?openid="+this.state.user.openid,method:"get"}).then(function(t){if(console.log(t.data.code),0===Number(t.data.code)){console.log("res.dat",t.data.data),n("SET_INFO_OBJ",t.data.data);var i=t.data.data;void 0===e.state.user.openid&&n("SET_OPENID",i.teaOpenid||i.stuOpenid),null===i||void 0===i.createTime||null===i.createTime||""===i.createTime?n("SET_REG_AUTH",!1):(n("SET_REG_AUTH",!0),void 0!==i.stuCode&&null!==i.stuCode?(n("SET_REG_STATUS",!0),null!==i.stuName&&""!==i.stuName?n("SET_REG_INFO",!0):n("SET_REG_INFO",!1)):void 0!==i.teaCode&&null!==i.teaCode?(n("SET_REG_STATUS",!0),null!==i.teaName&&""!==i.teaName?n("SET_REG_INFO",!0):n("SET_REG_INFO",!1)):n("SET_REG_STATUS",!1),void 0!==i.stuCode?n("SET_USR_TYPE","student"):void 0!==i.teaCode&&n("SET_USR_TYPE","teacher"))}},20)}}},dt=ct,mt={state:{courseName:"",courseId:"",subId:""},mutations:{SET_COURSE_NAME:function(t,e){t.courseName=e},SET_COURSE_ID:function(t,e){t.courseId=e},SET_ORDER_ID:function(t,e){t.subId=e}},actions:{}},ht=mt;u.a.use(ut.a);var pt=new ut.a.Store({modules:{user:dt,course:ht}}),ft=pt,vt={name:"app",watch:{$route:function(t,e){var n="";null===e.name&&(void 0===this.$route.query.id&&void 0!==localStorage.openid?n=localStorage.openid:void 0!==this.$route.query.id&&(n=this.$route.query.id,localStorage.setItem("openid",this.$route.query.id)),this.$store.commit("SET_OPENID",n),this.$store.dispatch("getInfo")),console.log("this.",e,t)}}},gt=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"app"}},[n("router-view")],1)},wt=[],_t={render:gt,staticRenderFns:wt},St=_t,Tt=n("VU/8"),bt=r,yt=Tt(vt,St,!1,bt,null,null),xt=yt.exports,Ct=n("U/Pg"),Et=n.n(Ct),kt=n("3BeM"),Rt=n("NXWw"),It=n("Y+qm"),Ot=n("62KO");u.a.use(d.a),u.a.use(Et.a),u.a.use(kt.a),u.a.use(Rt.a),u.a.use(It.a),u.a.use(Ot.a),c.a.attach(document.body),u.a.config.productionTip=!1,new u.a({router:rt,store:ft,render:function(t){return t(xt)}}).$mount("#app-box")},PTvS:function(t,e){},QKnK:function(t,e){},Y4Lg:function(t,e){},ZdaW:function(t,e){},rLAy:function(t,e,n){"use strict";function i(t){n("ruXm")}var o=n("xNvf"),s=(o.a,Boolean,Number,String,String,String,Boolean,String,String,{name:"toast",mixins:[o.a],props:{value:Boolean,time:{type:Number,default:2e3},type:{type:String,default:"success"},transition:String,width:{type:String,default:"7.6em"},isShowMask:{type:Boolean,default:!1},text:String,position:String},data:function(){return{show:!1}},created:function(){this.value&&(this.show=!0)},computed:{currentTransition:function(){return this.transition?this.transition:"top"===this.position?"vux-slide-from-top":"bottom"===this.position?"vux-slide-from-bottom":"vux-fade"},toastClass:function(){return{"weui-toast_forbidden":"warn"===this.type,"weui-toast_cancel":"cancel"===this.type,"weui-toast_success":"success"===this.type,"weui-toast_text":"text"===this.type,"vux-toast-top":"top"===this.position,"vux-toast-bottom":"bottom"===this.position,"vux-toast-middle":"middle"===this.position}},style:function(){if("text"===this.type&&"auto"===this.width)return{padding:"10px"}}},watch:{show:function(t){var e=this;t&&(this.$emit("input",!0),this.$emit("on-show"),this.fixSafariOverflowScrolling("auto"),clearTimeout(this.timeout),this.timeout=setTimeout(function(){e.show=!1,e.$emit("input",!1),e.$emit("on-hide"),e.fixSafariOverflowScrolling("touch")},this.time))},value:function(t){this.show=t}}}),a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"vux-toast"},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.isShowMask&&t.show,expression:"isShowMask && show"}],staticClass:"weui-mask_transparent"}),t._v(" "),n("transition",{attrs:{name:t.currentTransition}},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],staticClass:"weui-toast",class:t.toastClass,style:{width:t.width}},[n("i",{directives:[{name:"show",rawName:"v-show",value:"text"!==t.type,expression:"type !== 'text'"}],staticClass:"weui-icon-success-no-circle weui-icon_toast"}),t._v(" "),t.text?n("p",{staticClass:"weui-toast__content",style:t.style,domProps:{innerHTML:t._s(t.text)}}):n("p",{staticClass:"weui-toast__content",style:t.style},[t._t("default")],2)])])],1)},r=[],u={render:a,staticRenderFns:r},l=u,c=n("VU/8"),d=i,m=c(s,l,!1,d,null,null);e.a=m.exports},ruXm:function(t,e){},sk88:function(t,e){},vLgD:function(t,e,n){"use strict";var i=n("//Fk"),o=n.n(i),s=n("mtWM"),a=n.n(s),r=n("mw3O"),u=n.n(r),l=a.a.create({baseURL:"http://www.zhiheyikaoqin.cn/sdc/sdc/",timeout:5e4});l.interceptors.request.use(function(t){return t.headers["Content-Type"]="application/x-www-form-urlencoded","POST"!==t.method&&"post"!==t.method||(t.data=u.a.stringify(t.data)),t},function(t){o.a.reject(t),console.log(t)}),l.interceptors.response.use(function(t){return t},function(t){return console.log(t),o.a.reject(t)}),e.a=l}},["NHnr"]);
//# sourceMappingURL=app.151a514a3c3c93239d45.js.map