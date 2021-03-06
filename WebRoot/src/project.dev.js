require=(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({"_node_hall_click":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0f267234jFPTb11CEt7yqqI', '_node_hall_click');
// scripts\hall\_node_hall_click.js

'use strict';

/**
 * @class
 * @classdesc 本类挂载在 hall.sence场景中的 Canvas 上
 */
cc.Class({
    extends: cc.Component,

    onLoad: function onLoad() {
        window.pageLog.generateLogNode(); //生成页面调试节点
    },

    /**
     * 点击创建按钮时 的回调
     * 1.挂载在 创建房间面板中的 创建按钮节点上
     * @public
     */
    node_createBtnClick: function node_createBtnClick() {
        getGI('hall').loading('start'); //加载中...
        window.audio.playEffect('waterClick'); //播放点击声音
        var path = 'createRoomAlert/alertContent/createBg/';

        //局数打法：8局--1 16局--2， 分数打法：100分--3 200分--4
        var ju = cc.find(path + 'borderMethod/methodToggleGroup/ju8', this.node).getComponent(cc.Toggle).isChecked ? 1 : cc.find(path + 'borderMethod/methodToggleGroup/ju16', this.node).getComponent(cc.Toggle).isChecked ? 2 : cc.find(path + 'borderMethod/methodToggleGroup/fen100', this.node).getComponent(cc.Toggle).isChecked ? 3 : cc.find(path + 'borderMethod/methodToggleGroup/fen200', this.node).getComponent(cc.Toggle).isChecked ? 4 : 0;
        //底分：2分--1 4分--2 8分--3
        var score = cc.find(path + 'borderScore/scoreToggleGroup/toggle2', this.node).getComponent(cc.Toggle).isChecked ? 1 : cc.find(path + 'borderScore/scoreToggleGroup/toggle4', this.node).getComponent(cc.Toggle).isChecked ? 2 : cc.find(path + 'borderScore/scoreToggleGroup/toggle8', this.node).getComponent(cc.Toggle).isChecked ? 3 : 0;

        //番型：单游不能平到--1 双游不能平到--2
        var fan = cc.find(path + 'borderFan/fanToggleGroup/toggleSingle', this.node).getComponent(cc.Toggle).isChecked ? 1 : cc.find(path + 'borderFan/fanToggleGroup/toggleDouble', this.node).getComponent(cc.Toggle).isChecked ? 2 : 0;

        console.log({ message: { ju: ju, opt: [score, fan] }, type: 'create' });

        if (clientUser) {
            clientUser.send({ message: { ju: ju, opt: [score, fan] }, type: 'create' }); //发送创建房间的消息
        } else {
            getGI('hall').loading('end'); //加载完成
            getGI('hall').errorReminder('未连接到服务器!');
        }
    },

    /**
     * 历史记录界面 的显示或隐藏
     * 1.挂载在 “记录按钮”节点上
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     */
    node_recordAlertShow: function node_recordAlertShow(event, isShow) {
        window.audio.playEffect('waterClick'); //播放点击声音
        isShow = isShow === 'show' ? true : false;
        isShow && getGI('hall').loading('start'); //加载中...

        this._toggleAlert_('recordAlert', isShow); //隐藏或显示弹出框

        if (isShow) {
            if (clientUser) {
                clientUser.send({ type: 'record' }); //发送查询战绩记录消息
            } else {
                getGI('hall').loading('end'); //加载完成
                getGI('hall').errorReminder('未连接到服务器!');
            }
        }
    },

    /**
     * 设置界面 的显示或隐藏
     * 1.挂载在 “设置按钮”节点上 ,传入参数：'show'
     * 2.挂载在 设置界面的“关闭按钮”节点上 ,传入参数：'hide'
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
     */
    node_settingAlert: function node_settingAlert(event, isShow) {
        //设置框的显示和隐藏
        window.audio.playEffect('waterClick'); //播放点击声音

        isShow = isShow === 'show' ? true : false;
        isShow && getGI('hall').loading('start'); //加载中...

        this._toggleAlert_('settingAlert', isShow); //隐藏或显示弹出框
        isShow && getGI('hall').loading('end'); //加载完成
    },

    /**
    * 活动界面 的显示或隐藏
    * 1.挂载在 “活动按钮”节点上 ,传入参数：'show'
    * 2.挂载在 活动界面的“关闭按钮”节点上 ,传入参数：'hide'
    * @public
    * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
    * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
    */
    node_activityAlert: function node_activityAlert(event, isShow) {
        //设置框的显示和隐藏
        window.audio.playEffect('waterClick'); //播放点击声音

        isShow = isShow === 'show' ? true : false;
        isShow && getGI('hall').loading('start'); //加载中...

        this._toggleAlert_('activityAlert', isShow); //隐藏或显示弹出框
        isShow && getGI('hall').loading('end'); //加载完成
    },

    /**
     * 购买钻石(房卡)界面 的显示或隐藏
     * 1.挂载在 “头像旁边的+按钮”节点上 ,传入参数：'show'
     * 2.挂载在 购买钻石(房卡)界面的“关闭按钮”节点上 ,传入参数：'hide'
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
     */
    node_buyDiamondAlert: function node_buyDiamondAlert(event, isShow) {
        //设置框的显示和隐藏
        window.audio.playEffect('waterClick'); //播放点击声音

        isShow = isShow === 'show' ? true : false;
        isShow && getGI('hall').loading('start'); //加载中...

        this._toggleAlert_('buyDiamondAlert', isShow); //隐藏或显示弹出框
        isShow && getGI('hall').loading('end'); //加载完成
    },

    /**
     * 加入房间界面 的显示或隐藏
     * 1.挂载在 “加入房间按钮”节点上 ,传入参数：'show'
     * 2.挂载在 加入房间界面的“关闭按钮”节点上 ,传入参数：'hide'
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
     */
    node_joinRoomAlert: function node_joinRoomAlert(event, isShow) {
        //显示弹出框, isShow为true时，显示弹出框，否则隐藏
        window.audio.playEffect('waterClick'); //播放点击声音

        isShow = isShow === 'show' ? true : false;
        isShow && getGI('hall').loading('start'); //加载中。。。

        this._toggleAlert_('joinRoomAlert', isShow); //隐藏或显示弹出框

        if (isShow) {
            // event.target.setScale(1);
        } else {
            getGI('hall').inputNumPanel.reset();
        }
        isShow && getGI('hall').loading('end'); //加载完成
    },

    /**
     * 玩法弹出框界面 的显示或隐藏
     * 1.挂载在 “玩法按钮”节点上 ,传入参数：'show'
     * 2.挂载在 玩法弹出框界面的“关闭按钮”节点上 ,传入参数：'hide'
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
     */
    node_methodAlert: function node_methodAlert(event, isShow) {
        //显示弹出框, isShow为true时，显示弹出框，否则隐藏

        window.audio.playEffect('waterClick'); //播放点击声音

        isShow = isShow === 'show' ? true : false;

        //添加帮助内容
        if (isShow) {
            if (!this.methodLoaded) {
                this.methodLoaded = true;
                getGI('hall').loading('start'); //加载中。。。
            }

            var nodeItem = cc.find('methodAlert/alertContent/methodBg/scrollView/view/content', this.node);
            this._initHelpText_(nodeItem); //生成帮助内容
        }

        this._toggleAlert_('methodAlert', isShow); //隐藏或显示弹出框
    },

    /**
     * 生成帮助内容
     * @param {cc.Node} nodeItem -- 帮助内容的父节点
     */
    _initHelpText_: function _initHelpText_(nodeItem) {
        if (nodeItem.childrenCount === 0) {
            //说明还没生成过
            cc.loader.loadRes("prefabs/helpItem", function (err, helpItemPref) {
                var everyLen = 1000,
                    helpItem = void 0,
                    helpText = window.helpText;
                var num = Math.ceil(helpText.length / everyLen);

                for (var i = 0; i < num; i++) {
                    helpItem = cc.instantiate(helpItemPref);
                    helpItem.name = 'helpItem' + i;
                    helpItem.parent = nodeItem;
                    helpItem.getComponent(cc.Label).string = helpText.substring(i * everyLen, (i + 1) * everyLen);
                }
            });
        }
    },


    /**
     * 创建房间界面 的显示或隐藏
     * 1.挂载在 “创建房间按钮”节点上 ,传入参数：'show'
     * 2.挂载在 创建房间界面的“关闭按钮”节点上 ,传入参数：'hide'
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
     */
    node_createRoomAlert: function node_createRoomAlert(event, isShow) {
        //显示弹出框, isShow为true时，显示弹出框，否则隐藏
        window.audio.playEffect('waterClick'); //播放点击声音

        isShow = isShow === 'show' ? true : false;
        isShow && getGI('hall').loading('start'); //加载中。。。

        this._toggleAlert_('createRoomAlert', isShow); //隐藏或显示弹出框
        isShow && getGI('hall').loading('end'); //加载完成
    },

    /**
     * 切换弹出框的显示
     * @private
     * @param {string} alertName -- 弹出框的名字
     * @param {boolean} isShow -- true表示显示 ，false表示隐藏
     * @param {boolean} togglePanel -- true表示 隐藏选择面板或显示面板
     */
    _toggleAlert_: function _toggleAlert_(alertName, isShow) {
        this.node.getChildByName(alertName).active = isShow;
    }

});

cc._RFpop();
},{}],"_node_login":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a0569NsHRJKJ4roHfDcnyn+', '_node_login');
// scripts\login\_node_login.js

'use strict';

var AudioMng = require('audioMng');

/**
 * @class
 * @classdesc 本类挂载在 login.sence场景中的 Canvas 上
 */
cc.Class({
    extends: cc.Component,

    properties: {
        audio_clip: cc.AudioClip },

    /**
     * login.sence场景中的 Canvas节点加载完成后的回调
     * @callback
     */
    onLoad: function onLoad() {
        //创建音频控制对象
        if (audio) {
            return;
        }

        window.audio = new AudioMng({ //存入全局中
            dir: 'audio/' });

        window.audio.stopBgMusic(); //停止背景音乐
        window.audio.playMusic('hallBg'); //播放大厅页的背景音乐

        var matchArr = window.location.href.match(/state=(\d{6})/);

        if (matchArr) {
            window.userInfo && (window.userInfo.roomId = matchArr[1]);
        }

        // window.loading = this;
        TOOL.createSocket({ type: 'login', message: window.userInfo }); //如果没有打开过socket就打开一个socket

        //预加载游戏界面
        // cc.director.preloadScene('room');
        var self = this;

        window.addEventListener('online', function () {
            console.log('上线了');
            console.log(socket);
            if (window.socket && !window.socket.wsObj) {
                if (clientUser) {
                    var openId = clientUser.attr('openId');
                    TOOL.createSocket({ type: 'reConnect', openId: openId }, 'reLink'); //重连
                } else {
                    TOOL.createSocket({ type: 'login', message: window.userInfo }, 'reLink');
                }
            }

            // getGI('offlineAlert').showOfflineAlert('hide'); //隐藏离线提示弹出框
        });

        window.addEventListener('offline', function () {
            console.log('下线了');
            console.log(socket);
            // getGI('offlineAlert').showOfflineAlert('show'); //显示离线提示弹出框
        });
    }

});

cc._RFpop();
},{"audioMng":"audioMng"}],"_node_operatePanelEvent":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd62fckeyphAI5HPCeXk03v1', '_node_operatePanelEvent');
// scripts\room\view\event\_node_operatePanelEvent.js

'use strict';

/**
 * @class
 * @classdesc 管理选项面板上的点击事件的类， 本类挂载到 playingLayer节点上
 */
var _Node_operatePanelEvent = cc.Class({
    extends: cc.Component,

    /**
     * 操作选择的点击事件
     * 挂载于 Canvas/playingLayer/user0/operate/btns/btn0 节点上
     *
     * operateArr =  [
     *
     *  ]
     * @private
    */
    node_operateClick: function node_operateClick(event) {
        if (!clientUser.attr('isCanOperateClick')) {
            //如果点击事件被禁用
            return;
        }

        var selectIndex = event.target.name.slice(-1); //选择的是第几个选项，0，1，2...， 0上对应'过'这个节点
        console.log('operateClick--选择的是 ' + selectIndex + ' 个');

        this.view = clientUser.mahjongMng.mahView;

        //选择的按钮的名字 //'guo' || chi' || 'pong' ||'gang' || 'hu' || 'youJing' || 'shuangYou' || 'sanYou' || 'sanJingDao'
        var chooseName = this.view.panelAttr('showObj').showArr[selectIndex];

        var arr = this.view.showChoose(chooseName);

        var selIndex = 0;
        var operateArr = clientUser.attr('operateArr'); //注意，这个数组中没有 guo
        if (arr === null || arr.length === 1) {
            if (chooseName === 'guo') {
                //如果选择的是过
                this._process_({ t: 'guo' });
                return;
            }
            outer: for (var i = 0; i < operateArr.length + 1; i++) {
                var type = operateArr[i].t;
                switch (type) {
                    case 'mingGang':
                    case 'anGang':
                    case 'buGang':
                        if (chooseName === 'gang') {
                            selIndex = i;
                            break outer;
                        }
                        break;
                    default:
                        if (chooseName === type) {
                            selIndex = i;
                            break outer;
                        }
                        break;
                }
            };

            this._process_(operateArr[selIndex]);
        }
    },

    /**
     * 左侧的牌的点击事件
     * 挂载 在 Canvas/playingLayer/user0/operate/choose/opt3 节点上
     */
    node_chooseClick: function node_chooseClick(event) {
        var selectIndex = event.target.name.slice(-1); //选择的是第几个选项，0，1，2...， 0上对应'过'这个节点
        console.log('choosClick--选择的是 ' + selectIndex + ' 个');
        var selIndex = this.view.panelAttr('currentArr')[selectIndex];
        this._process_(clientUser.attr('operateArr')[selIndex]);
    },

    /**
     * 选择一项后的处理
     * @private
     * @param {object} operateObj -- {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] }, //吃
     */
    _process_: function _process_(operateObj) {
        this.view.panelAttr('currentArr', null); //置空

        clientUser.attr('chooseIten', operateObj); //将自己的选存下来
        this.view.panelAttr('isShow', 'hide'); //隐藏面板

        switch (operateObj.t) {
            case 'guo':
                if (clientUser.attr('isActive')) {
                    //如果自己是活动用户，说明是自己拿了牌的操作的过，而不是别人出的牌的操作的过，就不用发消息
                    this.view.outCardEventAttr('isCanOperate', true); //启用点击事件
                    this.view.outCardEventAttr('isCanPlay', true); //可出牌
                } else {
                    clientUser.send({ type: 'guo' }); //发送过的消息
                    this.view.outCardEventAttr('isCanOperate', true); //启用点击事件
                    // clientUser.mahjongMng.animate.processCardAni(operateObj.operateName); //过的动画
                }
                break;
            case 'chi':
            case 'mingGang':
            case 'pong':
            case 'buGang':
                clientUser.send({ type: operateObj.t, message: TOOL.converOperate(operateObj) });
                this.view.outCardEventAttr('isCanOperate', false); //禁用出牌的点击事件
                break;
            case 'hu': //点炮胡
            case 'ziMo':
            case 'tianHu':
            case 'sanJingDao':
                if (!operateObj.t) {
                    debugger;
                }
                clientUser.send({ type: operateObj.t, message: TOOL.converOperate(operateObj) });
                this.view.outCardEventAttr('isCanPlay', false); //不可出牌， 但可以有点击动作
                break;
            case 'youJing':
            case 'shuangYou':
            case 'sanYou':
                if (!operateObj.t) {
                    debugger;
                }
                // clientUser.outCard(operateObj.m[0]); //自己出一张游金的牌
                clientUser.send({ type: operateObj.t, message: TOOL.converOperate(operateObj) });
                this.view.outCardEventAttr('isCanPlay', false); //不可出牌， 但可以有点击动作
                break;
            case 'anGang':
                clientUser.operateItem(operateObj);
                this.view.outCardEventAttr('isCanOperate', true); //启用点击事件
                this.view.outCardEventAttr('isCanPlay', false); //不可出牌， 但可以有点击动作
                break;
        }
    },

    onLoad: function onLoad() {}
});

module.exports = _Node_operatePanelEvent;

cc._RFpop();
},{}],"_node_room_click":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a2cf0tEFvtKcqLiJyEXxkLy', '_node_room_click');
// scripts\room\view\roomLayer\_node_room_click.js

'use strict';

/**
 * @class
 * @classdesc 本类挂载在 room.sence场景中的 Canvas 上
 */
cc.Class({
    extends: cc.Component,

    onLoad: function onLoad() {
        console.log('调整room的高度');
        this.node.height = cc.winSize.height;
        var self = this;
        cc.view.setResizeCallback(function () {
            console.log('调整room的高度');
            self.node.height = cc.winSize.height;
            console.log(cc.winSize);
        });

        window.pageLog.generateLogNode(); //生成页面调试节点

        // this._test_dice_(); //(测试用) 测试中间的骰子
        // this._test_singleResult_();//(测试用) 测试单局结算界面的显示
        // this._test_allResult_();//(测试用) 测试八局结算界面的显示
        // this._test_mj_(); //(测试用) 测试麻将牌的显示
    },

    //(测试用) 测试八局结算界面的显示
    _test_allResult_: function _test_allResult_() {
        var test_AllObj = null;
        var allAlert = cc.find('Canvas/allResultAlert');

        function _test_allResultCallback_() {
            if (!test_AllObj) {
                var allResult = require('allResultAlert_display');
                test_AllObj = new allResult();
            }
            test_AllObj.refreshMahResult(testData.test_allResultData);
            allAlert.active = true;
        };

        if ('touches' in cc.sys.capabilities) {
            cc.find('Canvas').on('touchend', _test_allResultCallback_, this);
        } else if ('mouse' in cc.sys.capabilities) {
            cc.find('Canvas').on('mouseup', _test_allResultCallback_, this);
        }
    },

    //(测试用) 测试中间的骰子
    _test_dice_: function _test_dice_() {
        function _test_diceCallback_() {
            cc.find('Canvas').getComponent(cc.Animation).play('diceRun');
        }

        if ('touches' in cc.sys.capabilities) {
            cc.find('Canvas/roomMain/timer/border').on('touchend', _test_diceCallback_, this);
        } else if ('mouse' in cc.sys.capabilities) {
            cc.find('Canvas/roomMain/timer/border').on('mouseup', _test_diceCallback_, this);
        }
    },

    //(测试用) 测试单局结算界面的显示
    _test_singleResult_: function _test_singleResult_() {
        var test_SingleObj = null;
        var singleAlert = cc.find('Canvas/singleAlert');

        function _test_singleResultCallback_() {
            if (!test_SingleObj) {
                var SingleResult = require('singleResult_display');
                test_SingleObj = new SingleResult();
            }
            test_SingleObj.refreshMahResult(testData.test_singleResultData);
            singleAlert.active = true;
        }

        if ('touches' in cc.sys.capabilities) {
            cc.find('Canvas').on('touchend', _test_singleResultCallback_, this);
        } else if ('mouse' in cc.sys.capabilities) {
            cc.find('Canvas').on('mouseup', _test_singleResultCallback_, this);
        }
    },

    //(测试用) 测试麻将牌的显示
    _test_mj_: function _test_mj_() {
        var test_clickNum = 0,
            //记录点击次数
        test_direction = 'right',
            //手牌从左边还是右边拿 'right' 'left'
        test_model = 'hand',
            //手牌的立倒 'hand' 'handd
        test_hand = [],
            //手牌对象
        test_ming = [],
            //明牌对象
        test_out = []; //出的牌对象

        var playingLayer = cc.find('Canvas/playingLayer');

        function test_mjCallback_() {
            if (test_clickNum > 15) {
                test_clickNum++;
                return;
            } else if (test_clickNum > 7) {
                var cardName = testData.test_cardsData[test_clickNum % 4].naPaiCards.shift();
                if (test_clickNum % 4 !== 0) {
                    cardName = '';
                }
                if (test_clickNum > 11) {
                    cardName = ['o2', 'o3'];
                }
                test_hand[test_clickNum % 4].inCardL(cardName); //从左边拿牌
                test_clickNum++;
                return;
            } else if (test_clickNum > 3) {
                if (test_direction === 'left') {
                    test_hand[test_clickNum % 4].displayHandL(testData.test_cardsData[test_clickNum % 4].handCards); //手牌
                } else {
                    var cardName = testData.test_cardsData[test_clickNum % 4].naPaiCards.shift();
                    if (test_clickNum % 4 !== 0) {
                        cardName = '';
                    }
                    test_hand[test_clickNum % 4].inCardR(cardName); //手牌
                }

                test_clickNum++;
                return;
            }
            if (test_hand.length === 0) {
                var Test_handClass = require('handCardMng');
                var Test_outClass = require('outCardMng');
                var Test_mingClass = require('mingCardMng');
                var userNode = null;
                for (var i = 0; i < 4; i++) {
                    userNode = cc.find('user' + i, playingLayer);
                    test_hand.push(new Test_handClass(userNode));
                    test_ming.push(new Test_mingClass(userNode));
                    test_out.push(new Test_outClass(userNode));
                    userNode.active = false; //隐藏用户节点
                    test_hand[test_hand.length - 1].convertHM(test_model);
                }
                playingLayer.active = true; //显示麻将层

                // window.clientUser = {openId: '10000', cardsHandOrder: {}};
                // window.clientUser.attr = function(name, value){
                //     if(name === 'cardsHandOrder'){
                //         return window.clientUser.cardsHandOrder;
                //     }
                // };
            }

            test_direction === 'right' && test_hand[test_clickNum].displayHandR(testData.test_cardsData[test_clickNum].handCards); //手牌
            test_direction === 'left' && test_hand[test_clickNum].displayHandL(testData.test_cardsData[test_clickNum].handCards.length); //手牌
            test_ming[test_clickNum].displayAllMing(testData.test_cardsData[test_clickNum].optMsgs); //明牌
            test_out[test_clickNum].operateAllOutCard(testData.test_cardsData[test_clickNum].outCards); //出牌

            cc.find('user' + test_clickNum, playingLayer).active = true; //显示用户节点

            test_clickNum++;
        };

        //测试单局结算界面的显示
        if ('touches' in cc.sys.capabilities) {
            cc.find('Canvas').on('touchend', test_mjCallback_, this);
        } else if ('mouse' in cc.sys.capabilities) {
            cc.find('Canvas').on('mouseup', test_mjCallback_, this);
        }
    },

    /**
     * 退出房间按钮的点击事件
     * 挂载于 Canvas/bg/exitRoom
     */
    exitClick: function exitClick(event) {
        window.audio.playEffect('waterClick'); //播放点击声音

        clientUser.send({ type: 'exitRoom' }); //发送退出房间的消息

        //回到大厅页面
        cc.director.loadScene('hall', function () {
            window.reset();
            //显示用户的信息
            getGI('hall').display({
                nickName: clientUser.attr('nickName'), //昵称
                userId: clientUser.attr('userId'), //用户Id
                headIcon: clientUser.attr('faceIcon'), //头像图标
                isVip: clientUser.attr('isVip'), //是否是vip
                roomCardNum: clientUser.attr('roomCards') });
        });
    },

    /**
     * 邀请好友按钮的点击事件
     * 挂载于 Canvas/bg/inviteBtn
     * 挂载于 Canvas/sharFriend
     */
    inviteClick: function inviteClick(event, isShow) {
        window.audio.playEffect('waterClick'); //播放点击声音

        //显示提示
        room.displayRoomInfo({
            shareFriend: isShow
        });
    },

    /**
     * 设置界面 的显示或隐藏
     * 1.挂载在 “设置按钮”节点上 ,传入参数：'show'
     * 2.挂载在 设置界面的“关闭按钮”节点上 ,传入参数：'hide'
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     * @param {string} isShow -- 传入'show' 时显示 帮助界面，传入 'hide'时隐藏
     */
    node_settingAlert: function node_settingAlert(event, isShow) {
        //设置框的显示和隐藏
        window.audio.playEffect('waterClick'); //播放点击声音
        isShow = isShow === 'show' ? true : false;
        this.node.getChildByName('settingAlert').active = isShow;
    },

    /**
     * 返回微信按钮的点击事件
     * 挂载于 Canvas/bg/inviteBtn
     */
    node_wixinClick: function node_wixinClick(event, isShow) {
        console.log('返回微信按钮的点击事件');
        window.audio.playEffect('waterClick'); //播放点击声音
        window.close();
    },

    /**
     * 语音按钮的点击事件
     * 挂载于 Canvas/bg/inviteBtn
     */
    node_speachClick: function node_speachClick(event, isShow) {
        console.log('语音按钮的点击事件');
        window.audio.playEffect('waterClick'); //播放点击声音
    },

    /**
     * 头像的点击事件
     * 挂载于 Canvas/bg/inviteBtn
     */
    node_headClick: function node_headClick(event, isShow) {
        window.audio.playEffect('waterClick'); //播放点击声音
        isShow = isShow === 'show' ? true : false;
        cc.find('Canvas/userInfoAlert').active = isShow;
    },

    /**
     * 信息框的显示和隐藏
     * @public
     */
    node_messageClick: function node_messageClick(event, isShow) {
        window.audio.playEffect('waterClick'); //播放点击声音
        isShow = isShow === 'show' ? true : false;
        cc.find('Canvas/msgAlert').active = isShow;
    }

});

cc._RFpop();
},{"allResultAlert_display":"allResultAlert_display","handCardMng":"handCardMng","mingCardMng":"mingCardMng","outCardMng":"outCardMng","singleResult_display":"singleResult_display"}],"allResultAlert_click":[function(require,module,exports){
"use strict";
cc._RFpush(module, '55887Wsfg5IT4njLZSgY3g2', 'allResultAlert_click');
// scripts\room\view\alert\allResultAlert_click.js

'use strict';

/**
 * 八局结束弹出框, 挂载在 room场景中的总结算节点上
 *
*/
cc.Class({
        extends: cc.Component,

        /**
         * 八局统计弹出框的返回大厅按钮的点击事件
         * @public
         */
        node_goHallClick: function node_goHallClick(event) {
                room.resultAlert.allResultShow('hide'); //隐藏八局结算框
                window.audio.playEffect('waterClick'); //播放点击声音

                console.log('点击了总结算页面的返回大厅按钮');

                //重置环境
                room.resetMah();

                //返回大厅页面，并重置全局数据
                cc.director.loadScene('hall', function () {
                        TOOL.backHall();
                });
        },

        /**
         * 分享排行按钮的点击事件
         */
        node_shareResultClick: function node_shareResultClick() {
                room.resultAlert.allResultShow('hide'); //隐藏八局结算框
                window.audio.playEffect('waterClick'); //播放点击声音

                console.log('点击了总结算页面的分享排行按钮');

                //显示提示
                room.displayRoomInfo({ shareFriend: 'show' });

                //返回大厅页面，并重置全局数据
                // cc.director.loadScene('hall', function(){
                //    TOOL.backHall();
                // });
        }

});

cc._RFpop();
},{}],"allResultAlert_display":[function(require,module,exports){
"use strict";
cc._RFpush(module, '29993COshdJAZlSOjvJZMlk', 'allResultAlert_display');
// scripts\room\view\alert\allResultAlert_display.js

'use strict';

/**
 * 八局结束弹出框
 *
*/
var ResultAlert = cc.Class({
    name: 'ResultAlert',

    ctor: function ctor() {
        this.allResult = cc.find('Canvas/allResultAlert'); //弹出框节点
        this.allResultUserPref = getGI('roomGlobalRes').allResultUserPref; //预制节点
        this.isShow = false; //记录目前的显示状态
        this.allUserData = null; //记录收到的所有局的信息
        this.shareObj = []; //用于分享的分数
    },

    reset: function reset() {
        this.isShow = false; //记录目前的显示状态
        this.shareObj.length = 0;
    },

    /**
     * 八局统计弹出框的显示和隐藏
     * @public
     * @param {string} isShow -- 'show' 表示显示弹出框， 'hide'表示隐藏弹出框
     */
    allResultShow: function allResultShow(isShow) {
        this.isShow = isShow === 'show' ? true : false;
        this.allResult.active = this.isShow; //显示弹出框

        //隐藏时，要设置回去，而不能是八局的数据
        if (!this.isShow) {
            TOOL.replaceShareUrl(room ? room.roomId : 'wx');
        }
    },

    /**
    * 获取分享时的要设置的数据
    */
    _setShareObj_: function _setShareObj_(userId, roomScore) {
        var obj = {};
        obj.name = room.getUser(userId).attr('nickName');
        obj.score = roomScore;
        this.shareObj.push(obj);
    },

    /**
     * 设置并显示所有数据
     * @private
     * @param {Array} allUserData -- 四个用户的全部数据 , 注意，有可能只打了2局，就解散房间了
       allUserData = [ layer2, layer2, layer2, layer2 ];
     */
    refreshMahResult: function refreshMahResult(allUserData) {
        this.allUserData = allUserData;
        this._addHuInfo_(allUserData); //计算胡牌的类型等 huInfo
        this._convert_(); //转换数据的格式
        this._setOthers_();
        this._setDetails_(); //获取节点，并设置每一局的分数

        TOOL.replaceShareUrl(this.shareObj); //更改分享的设置
    },

    _convert_: function _convert_() {
        //{userId: 389233, zhuangNum: 1, huPaiNum: 3, dianPaoNum: 2, scores: 8, nickName: '我是昵称121', headIcon:'389.png'},
        var arr = [];
        this.allUserData.forEach(function (user) {
            var obj = {};
            obj.userId = user.userInfo.userId;
            obj.nickName = user.userInfo.nickName;
            obj.headIcon = user.userInfo.headIcon;

            obj.zhuangNum = user.huInfo.zhuangCount; //坐庄的总次数
            obj.huPaiNum = user.huInfo.huCount; //胡牌的总次数
            obj.dianPaoNum = user.huInfo.paoCount; //点炮总次数
            obj.scores = user.roomScore; //自己的总分
            arr.push(obj);
        });

        this.allUserData = arr;
    },


    /**
     * 计算胡牌的类型等
     */
    _addHuInfo_: function _addHuInfo_(juResults) {
        var huInfo = void 0,
            optMsgs = void 0;
        juResults.forEach(function (user) {
            huInfo = user.huInfo;
            optMsgs = user.optMsgs;

            huInfo.daoType = '';huInfo.huCard = '';
            TOOL.converOperate(optMsgs); //转换类型
            for (var i = 0; i < optMsgs.length; i++) {
                switch (optMsgs[i].t) {
                    case 'hu':
                    case 'sanJingDao':
                    case 'ziMo':
                    case 'youJing':
                    case 'shuangYou':
                    case 'sanYou':
                    case 'tianHu':
                        huInfo.daoType = optMsgs[i].t;
                        huInfo.huCard = optMsgs[i].c;
                        return;
                }
            }
        });
    },


    /**
     * 设置其它部分
     * @private
     * @param {cc.Node} userBox -- 存入用户节点
     * @param {string} openId -- 当前要设置的人的openId
     * @param {number} totalScore -- 当前用户的八局总分数
     */
    _setOthers_: function _setOthers_() {
        var parent = cc.find('alertContent/allResultBg', this.allResult);
        parent.getChildByName('roomId').getComponent(cc.Label).string = '房间号：' + room.roomId; //设置房间号

        var value = room.attr({ 'diFeng': undefined, 'fanXin': undefined }); //底分和番型
        var str = '底:' + value.diFeng + ' 番:' + value.fanXin;
        parent.getChildByName('method').getComponent(cc.Label).string = str; //设置本局的规则
    },

    /**
    * 创建用户节点, 并设置每一局的分数
    * @private
    */
    _setDetails_: function _setDetails_() {
        var minScore = 0,
            maxScore = 0,
            userInfo,
            userBox;
        for (var i = 0; i < 4; i++) {
            userInfo = this.allUserData[i];
            userBox = cc.find('alertContent/allResultBg/users/allResultUser' + i, this.allResult);

            if (!userBox) {
                //如果没有，就动态创建一个
                userBox = cc.instantiate(this.allResultUserPref);
                userBox.name = 'allResultUser' + i;
                userBox.parent = cc.find('alertContent/allResultBg/users', this.allResult);
            }

            TOOL.setUserHeadIcon(userBox.getChildByName('headIcon'), userInfo.headIcon); // 更改头像

            userBox.getChildByName('nickName').getComponent(cc.Label).string = userInfo.nickName; //设置昵称
            userBox.getChildByName('zhuang').getComponent(cc.Label).string = '坐庄: ' + userInfo.zhuangNum; //设置坐庄次数
            userBox.getChildByName('huPai').getComponent(cc.Label).string = '胡牌: ' + userInfo.huPaiNum; //设置胡牌次数
            userBox.getChildByName('dianPao').getComponent(cc.Label).string = '点炮: ' + userInfo.dianPaoNum; //设置点炮次数
            var scores = userInfo.scores;
            minScore = minScore > scores ? scores : minScore; //记录最低分
            maxScore = maxScore < scores ? scores : maxScore; //记录最高分
            this._setShareObj_(userInfo.userId, scores); //将分数存入分享数据中
            userBox.getChildByName('jiFeng').getComponent(cc.Label).string = '积分: ' + (scores > 0 ? '+' + scores : scores); //设置总得分;
        }

        this._showWinner_(minScore, maxScore); //设置显示‘大赢家’和‘点炮高手’
    },

    /**
     * 设置显示‘大赢家’和‘点炮高手’
     * @private
     * @param {number} minScore --本场最低分
     * @param {number} maxScore --本场最高分
     */
    _showWinner_: function _showWinner_(minScore, maxScore) {
        var minArr = [],
            maxArr = [];

        if (maxScore !== minScore) {
            //排除掉所有人的分数均为0的情况
            for (var i = 0; i < 4; i++) {
                this.allUserData[i].scores === minScore && minArr.push(i);
                this.allUserData[i].scores === maxScore && maxArr.push(i);
            }
        }

        for (var i = 0; i < 4; i++) {
            var userBox = cc.find('alertContent/allResultBg/users/allResultUser' + i, this.allResult);

            if (minArr.indexOf(i) !== -1) {
                userBox.getChildByName('diaoPao').active = true;
                userBox.getChildByName('winer').active = false;
            } else if (maxArr.indexOf(i) !== -1) {
                userBox.getChildByName('diaoPao').active = false;
                userBox.getChildByName('winer').active = true;
            } else {
                userBox.getChildByName('diaoPao').active = false;
                userBox.getChildByName('winer').active = false;
            }
        }
    }
});

module.exports = ResultAlert;

cc._RFpop();
},{}],"animate_countDown":[function(require,module,exports){
"use strict";
cc._RFpush(module, '2e4d5uFl15HeLP8LoglEAeG', 'animate_countDown');
// scripts\room\view\roomLayer\animate\animate_countDown.js

'use strict';

/**
 * @class
 * @classdesc 倒计时类，用于打牌时，牌桌中间的倒计时
 */
var Animate_countDown = cc.Class({
    name: 'Animate_countDown',

    /**
     * @constructor
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.compon = getGI('roomGlobalRes'); //用于挂载定时器, 注意这里用了全局变量

        this.timeBackNode = cc.find('Canvas/roomMain/timer'); //指示器节点的父节点
        this.timeLabel = this.timeBackNode.getChildByName('time').getComponent(cc.Label); //指示器上的倒计时节点

        this.timeBackArr = [];
        for (var i = 0; i < 4; i++) {
            this.timeBackArr.push(this.timeBackNode.getChildByName('' + i)); //四个指示器高亮节点，通过隐藏/显示其中某一个节点，达到高亮指示的目的
        }

        this.callback = null; //暂存计时器的回调函数
        this.activeIndex = 0; //当前活动用户
        this.orginalTime = +this.timeLabel.string;
    },

    /**重置本对象 */
    reset: function reset() {
        console.log('animate_countDown.js重置了。');
        this.stopTime();
        this.activeIndex = 0;
    },

    /**
     * 显示或隐藏中间的倒计时框
     * @public
     * @param {string} isShow -- 'show'表示显示，'hide'表示隐藏
     */
    timeShow: function timeShow(isShow) {
        isShow = isShow === 'show' ? true : false;
        this.timeBackNode.active = isShow; //倒计时框的显示或隐藏
    },

    /**
     * 骰子和倒计时显示的切换
     */
    diceToggle: function diceToggle(isShow) {
        if (isShow === 'show') {
            this.timeBackNode.getChildByName('time').active = false;
            this.timeBackNode.getChildByName('border').active = true;
        } else {
            this.timeBackNode.getChildByName('time').active = true;
            this.timeBackNode.getChildByName('border').active = false;
        }
    },

    /**
     * 显示中间的指示器,并指向当前活动用户
     * @public
     * @param {number} activeIndex -- 已经切换好了的当前活动用户的位置号, 如果传入 -1表示不指向任何一个人
     */
    displayPointer: function displayPointer(activeIndex) {
        this.totalTime = this.orginalTime;
        this.callback && this.compon.unschedule(this.callback); //先停止计时器

        if (this.activeIndex !== -1) {
            this.timeBackArr[this.activeIndex].stopAction(this.blinkAct); //停止原来的闪烁
            this.timeBackArr[this.activeIndex].stopAction(this.audioAct); //停止响铃
            this.blinkAct = null;this.audioAct = null;
        }

        this.activeIndex = activeIndex;
        for (var i = 0; i < 4; i++) {
            if (activeIndex === i) {
                this.timeBackArr[i].active = true; //当前用户的指示器亮
            } else {
                this.timeBackArr[i].active = false; //其它用户的指示暗
            }
        }
        this.timeLabel.string = this.totalTime;

        if (activeIndex !== -1) {
            this.callback = this._timerCallback_.bind(this);
            this.compon.schedule(this.callback, 1); //计时器
        }
    },

    /**
     * 中间的指示器上计时器的回调函数
     * @private
     */
    _timerCallback_: function _timerCallback_() {
        this.totalTime--;
        if (this.totalTime <= -1) {
            window.audio.playEffect('naozhong'); //播放闹钟声
            this.compon.unschedule(this.callback);
            //计时器每一秒钟闪烁一次, 一直闪烁，直到下次
            this.blinkAct = this.timeBackArr[this.activeIndex].runAction(cc.repeatForever(cc.blink(20, 20)));
            //每十秒钟响铃一次
            this.audioAct = this.timeBackArr[this.activeIndex].runAction(cc.repeatForever(cc.sequence(cc.delayTime(10), cc.callFunc(function () {
                window.audio.playEffect('naozhong'); //播放闹钟声
            }))));
            this.totalTime = 0;
        } else {
            this.timeLabel.string = this.totalTime < 10 ? '0' + this.totalTime : this.totalTime;
        }
    },

    /**
    * 停止中间的指示器上计时器
    * @public
    */
    stopTime: function stopTime() {
        this.compon.unschedule(this.callback);
    }

});

module.exports = Animate_countDown;

cc._RFpop();
},{}],"animate_head":[function(require,module,exports){
"use strict";
cc._RFpush(module, '87a69REFXFAuLhHoiez1kJB', 'animate_head');
// scripts\room\view\roomLayer\animate\animate_head.js

'use strict';

/**
 * @class
 * @classdesc 开始发牌后，用户头像会运动到一边
 */
var Animate_head = cc.Class({
    name: 'Animate_head',

    /**
     * @constructor
     */
    ctor: function ctor() {
        this.userPath = 'Canvas/roomMain/user'; //用户头像的节点路径
        this.finishedFunc = null; //动画完成后的回调
        this.originalP = []; //存放四个用户头像的原始坐标
        this._recordOriginalP_(); //记录四个头像的原始坐标
    },

    /**
     * 一局完成后头像恢复到原始位置
     * @public
     */
    recoverOriginalP: function recoverOriginalP() {
        console.log('执行了函数 recoverOriginalP');

        var wedget, pObj;
        for (var i = 0; i < 4; i++) {
            wedget = cc.find(this.userPath + i).getComponent(cc.Widget);
            pObj = this.originalP[i];
            for (var prop in pObj) {
                wedget[prop] = pObj[prop];
            }
        }
    },

    /**重置本对象 */
    reset: function reset() {
        console.log('animate_head.js重置了。');
        this.recoverOriginalP(); //一局完成后头像恢复到原始位置
        var animation = cc.find('Canvas').getComponent(cc.Animation);
        animation.off('finished', this.finishedFunc); //先取消动画完成后的回调
    },

    /**
      * 头像运动到一边
      * @public
      */
    runHead: function runHead() {
        console.log('执行了函数 runHead');
        var animation = cc.find('Canvas').getComponent(cc.Animation);
        animation.play('userHeadRun'); //播放动画 //动画挂在Canvas节点上
    },

    /**
     * 取消事件
     * @public
     */
    offFinish: function offFinish() {
        var animation = cc.find('Canvas').getComponent(cc.Animation);
        animation.off('finished', this.finishedFunc); //先取消动画完成后的回调
    },

    /**
     * 直接运动到一边，没有动画
     * @public
     */
    runHeadNoTime: function runHeadNoTime(finishedFunc) {
        this.finishedFunc = finishedFunc;
        var animation = cc.find('Canvas').getComponent(cc.Animation);
        if (finishedFunc) {
            animation.on('finished', finishedFunc);
        }
        var duration = animation.getAnimationState('userHeadRun').duration;
        setTimeout(function () {
            animation.play('userHeadRun', duration - 0.1);
        }, 400);
    },

    /**
     * 记录四个头像的原始坐标
     * @private
     */
    _recordOriginalP_: function _recordOriginalP_() {
        var wedget;
        for (var i = 0; i < 4; i++) {
            wedget = cc.find(this.userPath + i).getComponent(cc.Widget);
            switch (i) {
                case 0:
                    this.originalP.push({ left: wedget.left, bottom: wedget.bottom }); //存坐标
                    break;
                case 1:
                case 2:
                    this.originalP.push({ left: wedget.left, top: wedget.top }); //存坐标
                    break;
                case 3:
                    this.originalP.push({ right: wedget.right, top: wedget.top }); //存坐标
                    break;
            }
        }
    }

});

module.exports = Animate_head;

cc._RFpop();
},{}],"animate_outCard":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ddf95O3SAtJ8oVhTBZOa0nX', 'animate_outCard');
// scripts\room\view\userLayer\animate\animate_outCard.js

'use strict';

/**
 * @class
 * @classdesc 出牌动画类
 */
var Animate_outCard = cc.Class({
    name: 'Animate_outCard',

    /**
     * @constructor
     * @param {Object} config -- 配置参数
     * config = {
     *             userSelf //用户自己
     *          }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.userSelf = config.userSelf;
        this.userNode = config.userSelf.attr('userNode'); //用户节点
        this.cards_Atlas = config.userSelf.attr('cards_Atlas'); //文字的图集

        this.outCardPosition = null; //出牌的最终显示位置(即最终要运动到的位置)
        this.nextNode = null;

        this.handCardHeight = cc.p(0, cc.find('hand/handmah_0', this.userNode).height / 2); //手中牌的高度
        this.outCardBig = this.userNode.getChildByName('outCard'); //出牌时放大的那张牌, 简称大牌

        //坐标计算
        this.outCardCoord = this.outCardBig.getPosition(); //存大牌的坐标， 以便完成动画后可以重新回到这个位置
        this.outCardScaleX = this.outCardBig.scaleX; //存大牌的缩放比例， 以便完成动画后可以重新回到原来的大小
        this.outCardScaleY = this.outCardBig.scaleY; //存大牌的缩放比例， 以便完成动画后可以重新回到原来的大小
        this.out1Node = this.userNode.getChildByName('out1'); //out1节点
        this.scale = this.out1Node.getChildByName('mingmah_0').width / this.outCardBig.width; //
    },

    /**重置本对象 */
    reset: function reset() {
        log.log4('animate_outCard.js重置了。');
        this._outCardReset_(); //出牌的动画节点重置
    },

    /**
     * @param {cc.Node} nextNode -- 出牌的最终要显示的牌的节点
     * @param {string} card -- 出的牌: 'w2'
     * @param {function} callback -- 出牌动画完成后的回调
     */
    outCardAni: function outCardAni(nextNode, card, callback) {
        //出牌的动画

        this.nextNode = nextNode; //暂时存起来，可以在回调函数中使用
        this.callback = callback; //先将回调暂时存起来
        this.outCardPosition = nextNode.getPosition(); //牌的显示的位置坐标

        this._outCardReset_(); //回到原来状态

        this.outCardBig.getComponent(cc.Sprite).spriteFrame = this.cards_Atlas.getSpriteFrame("mingmah_" + card); //更换放大的牌

        this.outCardBig.active = true; //显示放大的牌

        this.outCardBig.runAction( //打牌的动画
        cc.sequence(cc.delayTime(0.8), cc.spawn(cc.moveTo(0.4, this._calcChildToGrand_(this.nextNode.parent, this.outCardPosition)), cc.scaleTo(0.4, this.out1Node.scaleX, this.out1Node.scaleX)), cc.callFunc(this._outCardAniFinish_, this)));
    },

    /**
     * 计算子节点，相对于爷爷节点的坐标
     * 一般情况下，子节点的坐标是相对于父节点的
     * @private
     * @param {cc.Node} grandNode -- 爷爷节点
     * @param {cc.Node} mePoint -- 要计算的子节点
     * @returns {cc.Vct2} -- 计算好的坐标
     */
    _calcChildToGrand_: function _calcChildToGrand_(grandNode, mePoint) {
        //说明：先将子节点缩放，然后相加
        return mePoint.mul(grandNode.scaleX).add(grandNode.getPosition());
    },

    /** 出牌动画完成后
     * @private
     */
    _outCardAniFinish_: function _outCardAniFinish_() {
        //
        log.log1('出牌动画完成、、、、');
        audio.play('give'); //声音
        var nextNodeName = 'mingmah_' + this.userSelf.mahjongMng.mahView.getOutPointerIndex();

        //如果回退了一格，则当前指向的节点就不是回退前的那个节点了，如果两个节点一样，就说明没在回退
        this.nextNode.active = true; //显示出的牌

        var self = this;
        if (this.nextNode.name !== nextNodeName) {
            setTimeout(function () {
                self.nextNode.active = false;
                room.finishOutSkipAni(); //隐藏跳动的小红点
            }, 200);
        }

        this.outCardBig.active = false; //隐藏运动的那张牌
        // log.log1('node坐标：', this.outCardPosition);
        //log.log1('node坐标：', this.outPointer.getP);
        var outHeight;
        if (-1 !== this.userNode.name.search(/[013]/)) {
            outHeight = this.outCardPosition.add(this.handCardHeight.mul(0.5));
        } else {
            outHeight = this.outCardPosition.add(this.handCardHeight);
        }
        var tempPosition = this._calcChildToGrand_(this.nextNode.parent, outHeight);

        // log.log1('牌的坐标是： ');
        // log.log1(tempPosition);
        var outPosition = this._calcChildToGrand_(this.userNode, tempPosition);
        // log.log1('最终的坐标是： ');
        // log.log1(outPosition);
        // this.playOutAni(outPosition);
        var outSkip = this._calcChildToGrand_(this.userNode.parent, outPosition);
        this.callback && this.callback(outSkip);
    },

    /**
     * 对出牌的动画节点进行重置
     * @private
    */
    _outCardReset_: function _outCardReset_() {
        this.outCardBig.setPosition(this.outCardCoord); //恢复坐标
        this.outCardBig.scaleX = this.outCardScaleX; //恢复缩放
        this.outCardBig.scaleY = this.outCardScaleY; //恢复缩放
    }
});

module.exports = Animate_outCard;

cc._RFpop();
},{}],"animate_skip":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8e350Zj4ylFw5S24nZy81rs', 'animate_skip');
// scripts\room\view\roomLayer\animate\animate_skip.js

'use strict';

/**
 * @class
 * @classdesc 出牌后，会在出的牌的位置上跳动一个小红点，本类处理该点的动画
 */
var Animate_skip = cc.Class({
    name: 'Animate_skip',

    /**
     * @constructor
     */
    ctor: function ctor() {
        this.animState = null; //用于存放播放动画时返回的数据，标记跳动动画的状态，可以用来判断首次调用该动画

        this.jumpNode = cc.find('Canvas/playingLayer/jumpNode'); //最新出的牌的跳动指示
        this.animation = this.jumpNode.getComponent(cc.Animation); //跳动动画
        this.animationName = 'outCardJump'; //跳动动画的名字
    },

    /**
     * 重置本对象
     * @public
     */
    reset: function reset() {
        this.finishOutSkipAni(); //停止最新出的牌的跳动动画
        this.animState = null; //跳动动画的状态重置
    },

    /**
     * 出牌后的跳动动画，指定在哪个位置上播放动画
     * @public
     * @param {cc.Vec2|cc.Node} coord -- 设置跳动点的显示位置到指定的坐标位置，或到某节点对象的中心位置
     */
    playOutSkipAni: function playOutSkipAni(coord) {
        coord = coord instanceof cc.Vec2 ? coord : this._convertCoord_(coord);
        if (!this.animState) {
            //第一次播放
            this.jumpNode.active = true; //显示小红点
            this.jumpNode.setPosition(coord);
            this.animState = this.animation.play(this.animationName); //播放并记录状态
        } else {
            if (!this.animState.isPlaying) {
                //如果已经停止
                this.jumpNode.active = true; //显示小红点
                this.jumpNode.setPosition(coord);
                this.animState = this.animation.play(this.animationName); //播放并记录状态
            } else {
                //如果正在播放，就只切换坐标
                this.jumpNode.setPosition(coord);
            }
        }
    },

    /**
     * 传入一个节点对象，计算跳动的小红点的位于传入节点的中心时，小红点相对于小红点父节点的坐标
     * @private
     */
    _convertCoord_: function _convertCoord_(node) {
        var worldV = node.convertToWorldSpace(cc.v2(node.width / 2, node.height / 2 + 5));
        return this.jumpNode.parent.convertToNodeSpace(worldV);
    },

    /**
     * 出牌后的跳动动画 完成
     * @public
     */
    finishOutSkipAni: function finishOutSkipAni() {
        this.animation.stop(this.animationName);
        this.jumpNode.active = false; //隐藏小红点
    }

});

module.exports = Animate_skip;

cc._RFpop();
},{}],"audioMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7998eR+55dG67r76z5J9Y/Z', 'audioMng');
// scripts\common\audioMng.js

'use strict';

/**
 * @class
 * @classdesc 用于播放同一个文件夹中的所有音频
 */
var AudioMng = cc.Class({
    name: 'AudioMng',
    /**
     * @constructor
     * @param {object} config --{
     *                              dir: 'audio/', //路径
     *                          }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.dir = config.dir; //音频文件的路径
        this.refresh();

        this.volumeEffectArr = []; //记录背景音乐的id，用于更改音量
    },

    refresh: function refresh() {
        this.extend = window.getGI('extension'); //声音文件的扩展名 .mp3
        this.language = window.getGI('language') === 'mingNan' ? 'mn' : 'pt'; //普通话 或 闽南语   mingNan  puTongHua
        this.effectVolume = window.getGI('effectVolume'); //音效(点击)
        this.musicVolume = window.getGI('musicVolume'); //音乐(背景音乐)
    },
    refreshVolume: function refreshVolume() {
        this.effectVolume = window.getGI('effectVolume'); //音效(点击)

        var musicVolume = window.getGI('musicVolume');

        if (musicVolume !== this.musicVolume) {
            this.musicVolume = musicVolume; //音乐(背景音乐)
            if (musicVolume) {
                if (room) {
                    this.playMusic('playingBg');
                } else {
                    this.playMusic('hallBg');
                }
            } else {
                this.stopBgMusic();
            }
        }
    },
    refreshLanguage: function refreshLanguage() {
        this.language = window.getGI('language') === 'mingNan' ? 'mn' : 'pt'; //普通话 或 闽南语   mingNan  puTongHua
    },


    /**
     * 播放音乐
     * 示例：play('w1.mp3'); play('w1'); //不加扩展名时，默认加上.ogg
     *      play('w1.ogg', true); //循环播放
     *      play('w1', false, 0.3); //单次播放，音量30%
     * @public
     * @param {string} name -- 在本文件夹中 音频的名字 如 ： playingBg 此时自动加上扩展名 this.extend, 也可传入 playingBg.mp3，这时不再加扩展名
     * @param {boolean} loop -- 可省略，省略时为 false, 表示只播放一次，传入true表示循环播放
     * @param {number} volume -- 可省略，音量，其值只能是 0.0 ~ 1.0
     */
    playMusic: function playMusic(name) {
        var loop = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : true;
        var volume = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 1;

        if (!this.musicVolume) {
            return;
        }
        // name += this.extend;

        var self = this;
        cc.loader.loadRes(this.dir + 'comm/' + name, function (err, audio) {
            self[name] = cc.audioEngine.play(audio, loop, volume);
            self.volumeEffectArr.push(self[name]);
        });
    },


    /**
     * 播放音效有关的声音
     * @param {*} name
     * @param {*} loop
     * @param {*} volume
     */
    playEffect: function playEffect(name) {
        var loop = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
        var volume = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 1;

        if (!this.effectVolume) {
            return;
        }
        // name += this.extend;

        var self = this;
        cc.loader.loadRes(this.dir + 'comm/' + name, function (err, audio) {
            self[name] = cc.audioEngine.play(audio, loop, volume);
        });
    },


    /**
     * 播放麻将有关的声音
     * @param {*} name
     * @param {*} loop
     * @param {*} volume
     */
    playMj: function playMj(name) {
        var loop = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
        var volume = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 1;

        if (!this.effectVolume) {
            return;
        }
        // name += this.extend;

        var self = this;
        console.log(this.dir + this.language + '/' + name);
        cc.loader.loadRes(this.dir + this.language + '/' + name, function (err, audio) {
            self[name] = cc.audioEngine.play(audio, loop, volume);
        });
    },

    /**
     * 播放聊天有关的声音
     * @param {*} name
     * @param {*} loop
     * @param {*} volume
     */
    chat: function chat(name) {
        var loop = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
        var volume = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 1;

        if (!this.effectVolume) {
            return;
        }
        // name += this.extend;

        var self = this;
        cc.loader.loadRes(this.dir + 'chat/' + name, function (err, audio) {
            self[name] = cc.audioEngine.play(audio, loop, volume);
        });
    },


    /**
     * 更改文件夹的路径
     * @public
     * @param {string} dir -- 新的路径
     */
    setDir: function setDir(dir) {
        if (!dir) {
            return;
        }
        this.dir = dir;
    },

    /**
    * 停止播放指定的音乐
    * @public
    * @param {string} name -- 在本文件夹中 音频的名字 如 ： playingBg.mp3, 如果先前没有播放，则直接退出
    */
    stop: function stop(name) {
        if (this[name] === undefined) {
            return;
        }
        cc.audioEngine.stop(this[name]); //setVolume ( audioID  volume )
        delete this[name];
    },

    /**
     * 更改背景音量
     * @public
     * @param {number} volume -- 要设置的音量大小
     */
    setBgVolume: function setBgVolume(volume) {
        var arr = this.volumeEffectArr;
        for (var i = 0; i < arr.length; i++) {
            cc.audioEngine.setVolume(arr[i], volume);
        }
    },

    /**
     * 停止背景音乐
     * @public
     */
    stopBgMusic: function stopBgMusic() {
        var arr = this.volumeEffectArr;
        for (var i = 0; i < arr.length; i++) {
            cc.audioEngine.stop(arr[i]);
        }

        arr.length = 0; //清空
    }

});

module.exports = AudioMng;

cc._RFpop();
},{}],"buGangCheck_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '42b5aleTAhH3pFt72I9vYsq', 'buGangCheck_controller');
// scripts\common\controller\messageController\buGangCheck_controller.js

'use strict';

/**@class
 * @classdesc 补扛时，别人要检查能不能胡
 */
var BuGangCheck_controller = cc.Class({
    name: 'BuGangCheck_controller',

    ctor: function ctor() {},

    /**
     * 相要补扛的消息
     * data: {  e | errMsg: null,
                m | message: {t:50, m: '{t: 51, c: card, index: i, from: '38992', m: ['w2']}', 'p': '10120'} //别人对出的牌的可以进行的处理， 需要转换格式
                s | success: true, t | type: 50, operate
            }
     */
    wantBuGang_controller: function wantBuGang_controller(data) {
        console.log('执行 wantBuGang_controller...');
        var message = data.message.m;
        //对收到消息进行字段名的转换
        var operateMsg = {};
        operateMsg.operateName = data.type;
        operateMsg.operateCard = message.c;
        operateMsg.index = message.index;
        operateMsg.from = message.from;

        var openId = data.message.p; //补扛人的openId


        // 如果不是自己发的消息才处理, 因为自己的出牌已经在点击出牌时已经处理过了
        if (!room.msgIsMeSend(openId)) {
            //如果是特殊的补扛，不管是否成功，都要减一张
            if (window.messageType[message.t] === 'specialBuGang') {
                var user = room.getUser(openId);
                var mahjongMng = user.mahjongMng;
                mahjongMng.mahModel.operateCard(operateMsg.operateCard, 1, 'sub');
                //刷新手中的牌的显示
                var cards = mahjongMng.mahModel.getHandCard();
                mahjongMng.mahView.displayHand(cards);
            }

            //检查能对这张牌的操作
            clientUser.othersBuGangCheck({ type: 50, message: operateMsg, openId: openId });
        }

        room.displayPointer(-1); //停止中间的计时器，并且不指向任何一个人

        socket.finishMsg('wantBuGang'); //标记本条消息处理完成
    }

});

module.exports = BuGangCheck_controller;

cc._RFpop();
},{}],"calcHuMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '1286bo7oF1A367gSwvVWDIm', 'calcHuMng');
// scripts\room\model\cardsMng\calcHuMng\calcHuMng.js

'use strict';

var CardsHu_pair = require('cardsHu_pair');
var CardsHu_other = require('cardsHu_other');

var calcHuMng = cc.Class({
    name: 'calcHuMng',

    /**
     * @constructor
     * @param {Object} config -- {
     *                              cardsMng: cardsMng, //牌的管理对象
     *                           }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.cardsMng = config.cardsMng; //牌的管理对象
        this.cardsHu_pair = new CardsHu_pair({ //创建用于有将牌时的胡牌计算对象
            cardsMng: this.cardsMng
        });
        this.cardsHu_other = new CardsHu_other({ //创建用于有将牌时的胡牌计算对象
            cardsMng: this.cardsMng
        });
        this.hasCalcHuFirst = false; //是否计算了第一次胡
        this.statisticsArr = []; //胡牌数组，注意，里面可能有重复的牌
        this.allHuCardsArr = []; //存放胡牌的计算结果
    },

    reset: function reset() {
        console.log('calcHuMng.js重置了。');
    },

    /**在还没拿牌时调用，看能胡什么牌
     * 获得所有可以胡的牌，没有则返回空 [], 有的话就返回 [{...}, {....}]
     * @public
     * @returns {Array} -- /**this.allHuCardsArr的格式(最少也是一个空数组)
         * [
         *    [].name = 'thirty' .huCard = ['w2', 't3', 'o2'] //十三烂
         *    [{name:'t2', num: 2}, {name: 'w4', num: 4}, {'name': 'w5', num: 2}].huCard = ['w5'] .name = 'sevenPair' //小七对
         *
         *    //数组中是胡牌后的牌，huCard属性的值指出胡的是哪张牌， 注意：num = 2 的只有唯一一个，它就是将牌
         *    [ //这个是调用 cardsHu_pair 来判断的结果, jiangCard指将牌
         *      [{name: 'e', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w9', num: 2}].jiangCard = 'w9' ,
         *      [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w2' ,
         *      [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 't6', num: 2}].jiangCard = 't6' ,
         *    ].huCard = ['t6'] .name = 'jiang1'
         *    [
         *      [{name: 'e', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w2' ,
         *      [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w4', num: 2}].jiangCard = 'w4' ,
         *    ].huCard = ['t9'] .name = 'jiang2'
         *
         *    [].name = 'jiaHu'   .huCard = ['w1', ..., 'w9'] //假胡
         * ]
      */
    getAllCanHuCards: function getAllCanHuCards() {
        this.hasCalcHuFirst = true; //标记计算了一次
        console.log("getAllCanHuCards-手中的牌是： " + this.cardsMng.getAllCardsStr());

        this.allHuCardsArr.length = 0;

        //  result =  {
        //              isNeedContinue: true, //标志是否还要继续检查其它教, true表示还要继续
        //              resultArr: [arr1, arr2, ...], //检查的结果
        //            };
        var result = this.cardsHu_other.getJiao(); //假胡的教、十三烂的教、小七对的教
        if (!result.isNeedContinue) {
            //如果不需要继续检查
            this.allHuCardsArr.push(result.resultArr);
            return;
        }

        this._calcPairType(); //处理有一对将牌的胡牌的情况
        this._convertToJiaoArr(); //将计算结果转换成一个 教组成的一维数组

        console.log('cardsHu: 有教的计算结果：');
        console.log(this.allHuCardsArr);
        return this.allHuCardsArr;
    },

    /**
     * 获取所有的教, 注意，这个函数不具有计算功能，要想获取最新的教，只能先调用 , getAllCanHuCards() 来计算一次
     * @public
     * @returns {false_|_Array} -- false 或 ['w3', 'w2']
     */
    getAllJiao: function getAllJiao() {
        if (this.statisticsArr.length > 0) {
            return this.statisticsArr;
        } else {
            return false;
        }
    },

    /**
     * 检查是否胡指定的某张牌, 注意，这个函数不具有计算功能，要想获取最新的教，只能先调用 , getAllCanHuCards() 来计算一次
     * @public
     * @returns {boolean} -- 能胡，就返回true, 否则返回 false
     */
    checkHasHu: function checkHasHu(cardName) {
        for (var i = 0; i < this.statisticsArr.length; i++) {
            if (this.statisticsArr[i] === cardName) {
                return true;
            }
        }
        return false;
    },

    /**
     * 遍历胡牌的结果数组
     * @public
     * @param {function} callback -- 必须，遍历的回调函数 callback.call(thisObj, callbackArg);
     *                                      其中 callbackArg = {type: 'jiaHu', huCardArr: ['w3', 'w5'], huDetail: [....], i: 0 };
     *                               callback: 返回 'break' 表示break循环， 'continue'表示continue循环
     * @param {object} thisObj -- 可省略，指定在本循环中的this的指向
     */
    huResultForEach: function huResultForEach(callback, thisObj) {
        thisObj = thisObj || this;

        var result,
            //回调函数的返回值
        allHuCardsArr = this.allHuCardsArr,
            //胡牌的计算结果数组
        callbackArg = { type: '', huCardArr: null, huDetail: null, i: 0 }; //传给回调函数的参数

        for (var i = 0; i < allHuCardsArr.length; i++) {

            callbackArg.type = allHuCardsArr[i].name;
            callbackArg.huCardArr = allHuCardsArr[i].huCard;
            callbackArg.huDetail = allHuCardsArr[i];
            callbackArg.i = i;

            result = callback.call(thisObj, callbackArg);
            switch (result) {
                case 'break':
                    //表示break循环
                    break;
                case 'continue':
                    //表示continue循环
                    continue;
            }
        }
    },

    /**
     * 处理有一对将牌的胡牌的情况
     * 如果有能胡的牌，就push 到 this.allHuCardsArr中
     * @private
     */
    _calcPairType: function _calcPairType() {
        var num = 0; //每检测到能胡一张牌，这个值就增加1

        //依次试探34张牌，看能胡其中的哪些牌
        //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
        this.cardsMng.forEach(function (data) {

            //以下这几个if语句，作用是跳过明显的不可能胡牌的情况
            if (this._skip.call(this, data) === 'continueInner') {
                return 'continueInner'; //continue内层循环
            }

            /*resultArr的格式为：
                  false |   [ //同样是以w2为将牌，可以有好几种不同的胡牌方法, 但是本算法中，对于一种将牌，只检查一次，只要能胡，就会立即检查下一对将牌
                              //所以下面不会出现 jiangCard 相同的情况
                                [{name: 'e', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w2' ,
                                [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w8' ,
                                [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 't5' ,
                            ]
             */
            var resultArr = this.cardsHu_pair.checkHasHu(data.cardName); //查看所有手中的牌不能胡
            console.log('getAllCanHuCards：检查的结果是：', resultArr);

            if (resultArr) {
                //如果能胡
                num++;
                resultArr["huCard"] = [data.cardName]; //注意，这里是一个数组
                resultArr.name = 'jiang' + num;
                this.allHuCardsArr.push(resultArr);
            }
        }, this);
    },

    /**
    * 将计算结果转换成一个 教组成的一维数组
    * 转换结果： [] 或 ['w3', 't5']
    * @private
    */
    _convertToJiaoArr: function _convertToJiaoArr() {
        //计算是否已经有教了, 可能返回一个所有的教的牌数组，或false
        var result = this.allHuCardsArr; //教的计算结果
        this.statisticsArr = [];
        if (result.length > 0) {
            //如果有教
            for (var i = 0; i < result.length; i++) {
                this.statisticsArr = this.statisticsArr.concat(result[i].huCard);
            }
        }
    },

    /**
     * 作用是跳过明显的不可能胡牌的情况, 本函数专用于 this.getAllCanHuCards函数中
     * @private
     * @param {Object} data -- data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
     * @returns {string|undefined} -- 'continueInner'表示不对于本张牌来说，是不可以胡牌的,所以应该跳过, undefined表示可能胡牌
    */
    _skip: function _skip(data) {
        var i = data.i,
            j = data.j,
            typeName = data.typeName,
            innerArr = data.arr;
        if (typeName === 'o' || typeName === 't' || typeName === 'w') {
            if (data.num === 0 || data.num === 3) {
                if ((innerArr[j - 1] === 0 || innerArr[j - 1] === undefined) && (innerArr[j + 1] === 0 || innerArr[j + 1] === undefined)) {
                    return 'continueInner'; //continue内层循环
                }
                if ((innerArr[j - 1] === 0 || innerArr[j - 1] === undefined) && (innerArr[j + 2] === 0 || innerArr[j + 2] === undefined)) {
                    return 'continueInner';
                }
                if ((innerArr[j - 2] === 0 || innerArr[j - 2] === undefined) && (innerArr[j + 1] === 0 || innerArr[j + 1] === undefined)) {
                    return 'continueInner';
                }
            }
        }
    }
});

module.exports = calcHuMng;

cc._RFpop();
},{"cardsHu_other":"cardsHu_other","cardsHu_pair":"cardsHu_pair"}],"calc":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e499dQj3GhA4rYhG1pQxUWn', 'calc');
// scripts\server\serverController\calc.js

"use strict";

cc._RFpop();
},{}],"cardsAll":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a9abazO2+hHHKjoPmhKcI/E', 'cardsAll');
// scripts\room\model\cardsMng\cardsOperate\cardsAll.js

'use strict';

/**
 * @class
 * @classdesc -- 管理自己的牌
 */
var CardsAll = cc.Class({ //牌的信息管理
    name: 'CardsAll',

    /**
     * @constructor
     * @param {object} config -- {
     *                              handCards //必须，手中的牌
     *                              outCards //必须，出的的牌
     *                              operateCards //必须 操作的牌
     *                              operateCards_operate  //必须，操作的牌的一个扩展
     *                          }
     */
    ctor: function ctor() {
        var config = arguments[0];

        this.handCards = config.handCards; //手中的牌
        this.outCards = config.outCards; //出的的牌

        this.operateCards = config.operateCards; //操作的牌
        this.operateCards_operate = config.operateCards_operate; //操作的牌的一个扩展
    },

    /**
     * 操作出的牌
     */
    outArr: function outArr() {
        return this.outCards.outArr.apply(this.outCards, arguments);
    },

    /**
     * 一次性设置多个操作
     * @public
     */
    operateAttr: function operateAttr() {
        return this.operateCards.operateAttr.apply(this.operateCards, arguments);
    },

    /**
     * 每一局开始时重置对象中的参数
     * @public
    */
    reset: function reset() {
        console.log('cardsAll.js重置了。');
    },

    /**
     * 出牌操作(一次只能出一张牌)
     * @public
     * @param {string} cardName -- 要出的牌的名字 如: 'w2'
     */
    outCard: function outCard(cardName) {
        this.outCards.pushCard(cardName);
        this.handCards.operateCard(cardName, 1, 'sub');
    },

    /**
     * 拿牌操作(一次只能拿一张牌)
     * @public
     * @param {string} cardName -- 要拿的牌的名字 如: 'w2'
     */
    inCard: function inCard(cardName) {
        this.handCards.operateCard(cardName, 1, 'add');
    },

    /**
     * 进行操作
     * @public
     * @param {object} operateObj -- {p: '289383',  t: 'chi', c: 'w2', from: '3892938', m:['w1', 'w3', 'w2'] },//吃
     */
    operate: function operate(operateObj) {
        var cardsArr = operateObj.m;
        if (operateObj.t === 'buHua') {
            this.handCards.operateCard(operateObj.c, 1, 'sub');
        } else if (operateObj.t === 'chi') {
            for (var i = 0; i < cardsArr.length; i++) {
                if (operateObj.c !== cardsArr[i]) {
                    this.handCards.operateCard(cardsArr[i], 1, 'sub'); //吃的时候 要少减一张
                }
            }
        } else {
            for (var i = 0; i < cardsArr.length; i++) {
                this.handCards.operateCard(cardsArr[i], 1, 'sub');
            }
        }

        return this.operateCards.addOperate(operateObj);
    }

});

module.exports = CardsAll;

cc._RFpop();
},{}],"cardsHu_other":[function(require,module,exports){
"use strict";
cc._RFpush(module, '92970jM/YdAVZVDRcleOE5L', 'cardsHu_other');
// scripts\room\model\cardsMng\calcHuMng\cardsHu_other.js

'use strict';

var Tool = require('tools');
var CardsHu_pair = require('cardsHu_pair');

/**
 * @class
 * @classdesc 本类专门处理：没有将牌时的胡牌情况
 */
var CardsHu_other = cc.Class({
    name: 'CardsHu_other',

    /**
     * @constructor
     * @param {Object} config -- {
     *                              cardsMng: cardsMng, //牌的管理对象
     *                           }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.cardsMng = config.cardsMng; //牌的管理对象
    },

    /**
     * 检查假胡的教、十三烂的教、小七对的教
     * @public
     * @returns {Object} -- result = {
                                        isNeedContinue: true, //标志是否还要继续检查其它教
                                        resultArr: [arr1, arr2, ...], //检查的结果
                                    };
                                    其中：arr1 = [] , arr1.name = 'jiaHu', arr1.huCard = ['w2', 'e', 't4']
                                         arr2 = [ {name: 'w2', num: 2}, {name: 't5', num: 4} ] , arr1.name = 'seven', arr2.huCard = ['w3']
     */
    getJiao: function getJiao() {
        var result = {
            isNeedContinue: true, //标志是否还要继续检查其它教
            resultArr: [] };
        var jiaoCalcResult;

        if (jiaoCalcResult = this._isThirteenMessy()) {
            //如果有十三烂的教，就不可能再有其它的教
            result.resultArr.push(jiaoCalcResult);
            result.isNeedContinue = false;
        } else if (jiaoCalcResult = this._isJiaHu()) {
            //如果有假胡的教，就不可能再有其它的教
            result.resultArr.push(jiaoCalcResult);
            result.isNeedContinue = false;
        } else if (jiaoCalcResult = this._isSevePair()) {
            //如果有小七对的教, 则还有可能有一对将牌的教
            result.resultArr.push(jiaoCalcResult);
            result.isNeedContinue = true;
        }
        return result;
    },

    /**
     * 检查是不是有假胡的教
     * @private
     * @returns {boolean|Array} -- 返回false则没有教， 如果有教，就返回一个空数组
     *                          arr = [] , arr.name = 'jiaHu', arr.huCard = ['w2', 'e', 't4']
     */
    _isJiaHu: function _isJiaHu() {

        //就看是不是能假胡(注意，只有清一色才能出现假胡这种情况)
        var isSameClr = this.cardsMng.isSameColor(); // --> false表示不是清一色, 否则返回  'z'
        if (isSameClr) {
            //如果是清一色
            switch (isSameClr) {// ''| 'o' | 't' | 'w' | 'z'
                case 'o':
                case 't':
                case 'w':
                    var jiaHuCards = [];
                    for (var i = 1; i <= 9; i++) {
                        jiaHuCards.push(isSameClr + i);
                    }
                    break;
                case 'z':
                    var jiaHuCards = ['e', 's', 'x', 'n', 'z', 'f', 'b'];
                    break;
            }
            var jiaHuResult = [];
            jiaHuResult.name = 'jiaHu';
            jiaHuResult.huCard = jiaHuCards;
            return jiaHuResult;
        }

        return false;
    },

    /**检查是不是有小七对的教
     * @private
     * @returns {boolean|Array} -- 返回false则没有教，
     * 如果有教，就返回一个胡牌后的数组 result = [ {name: 'w2', num: 2}, {name: 't5', num: 4} ] , result.name = 'seven', result.huCard = ['w3']
    */
    _isSevePair: function _isSevePair() {
        //是不是小七对, false不是小七对, 返回一个对象则是小七对
        var result = true;
        var isHasSingle = false; // 开关，保证有且仅有一种单牌
        var resultArr = []; //结果数组
        resultArr.name = 'seven';
        if (this.cardsMng.getLen() === 13) {
            //只有手中有13张牌，才可能是小七对
            //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
            this.cardsMng.forEach(function (data) {
                if (data.num === 1 || data.num === 3) {
                    if (isHasSingle === false) {
                        isHasSingle = true;
                        resultArr['huCard'] = [data.cardName]; //注意， 这里是一个数组
                        resultArr.push({ name: data.cardName, num: ++data.num }); //注意，这里加了一个
                    } else {
                        result = false;
                        return 'breakOuter'; //退出整个循环
                    }
                } else {
                    //data.num 为 0，2，4
                    data.num !== 0 && resultArr.push({ name: data.cardName, num: data.num });
                }
            });
        } else {
            result = false;
        }
        return result && resultArr;
    },

    /**检查是不是有十三烂的教
     * @private
     * @returns {boolean|Array} -- 返回false则没有教， 就返回一个空数组
     *                          arr = [] , arr.name = 'thirty', arr.huCard = ['w2', 'e', 't4']
    */
    _isThirteenMessy: function _isThirteenMessy() {
        var result = true;
        //wordCards:用于存放可能胡的字牌
        //ordinalCards：用于存放可能胡的序数牌
        var resultObj = { 'wordCards': [], 'ordinalCards': [] };
        var resultArr = [];
        if (this.cardsMng.getLen() === 13) {
            //只有手中有13张牌，才可能是十三烂
            //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
            this.cardsMng.forEach(function (data) {
                if (data.num > 1) {
                    //如果有对子、刻子或扛子，则不是十三烂
                    result = false;
                    return 'breakOuter'; //退出整个循环
                }
                if (data.typeName === 'f' || data.typeName === 'j') {
                    //如果是字牌
                    if (data.num === 0) {
                        resultObj.wordCards.push(data.cardName); //如果某张牌的数量为0，就存起来
                    }
                }
                var j = data.j;
                if (data.num === 1) {
                    //如果某张牌的数量为1
                    if (data.typeName === 'o' || data.typeName === 't' || data.typeName === 'w') {
                        //只有这三种才计算相邻的牌
                        if ((data.arr[j + 1] === 0 || data.arr[j + 1] === undefined) && (data.arr[j + 2] === 0 || data.arr[j + 2] === undefined)) {} else {
                            //对于接下来连续两个牌，只要其中有一张的数量大于0，则不是十三烂
                            result = false;
                            return 'breakOuter'; //退出整个循环
                        }
                        for (var k = j + 3; k < data.arr.length; k++) {
                            if (data.arr[k] === 0) {
                                resultObj.ordinalCards.push(data.cardName);
                            } else {
                                break;
                            }
                        }
                    }
                }
            });
        } else {
            result = false;
        }

        if (result) {
            resultArr.name = 'thirty';
            resultArr.huCard = resultObj.wordCards.concat(resultObj.wordCards); //['e', 'w5']
            return resultArr; //返回一个空数组
        } else {
            return false;
        }
    }
});

module.exports = CardsHu_other;

cc._RFpop();
},{"cardsHu_pair":"cardsHu_pair","tools":"tools"}],"cardsHu_pair":[function(require,module,exports){
"use strict";
cc._RFpush(module, '894cdTbRTNA2rxLWEEIEbyx', 'cardsHu_pair');
// scripts\room\model\cardsMng\calcHuMng\cardsHu_pair.js

'use strict';

/**
 * @class
 * @classdesc 本类专门处理：有一对将牌时的胡牌情况
 *  词语说明：“通过检测”这个词，用于表明，到目前为止，还是可能胡牌，所能还要继续看其它的牌是否能胡牌
 *           “没有通过检测”这个词，用于表明，到目前为止，已经不可能胡牌了，所以不再继续检测，而是退出整个检测逻辑，去执行其它的逻辑
 */
var CardsHu_pair = cc.Class({
    name: 'CardsHu_pair',

    /**
     * @constructor
     * @param {Object} config -- {
     *                              cardsMng: cardsMng, //牌的管理对象
     *                           }
     */
    ctor: function ctor() {
        var config = arguments[0];

        /* false表示 中发白三个不一样的不能组成一个顺子，只能用来碰，刻子，各种扛。
         * true表示 中发白三个不一样的能组成一个顺子，只能用来碰，刻子，各种扛
         */
        this.isZFBOrder = room.attr('isZFBOrder');;

        this.cardsMng = config.cardsMng; //牌的管理对象
    },

    reset: function reset() {
        console.log('cardsHu_pair.js重置了。');
    },

    /**
     * 检查手中的牌是不是已经胡了(注意，此时手中有14张，而不是13张)
     * @public
     * @param {string} cardName -- 要检查的牌的名字
     * @returns {Object} -- false |
     *                      [ //同样是以w2为将牌，可以有好几种不同的胡牌方法, 但是本算法中，对于一种将牌，只检查一次，只要能胡，就会立即检查下一对将牌
     *                         //所以下面不会出现 jiangCard 相同的情况
                                [{name: 'e', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w2' ,
                                [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w8' ,
                                [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 't5' ,
                            ]
    */
    checkHasHu: function checkHasHu(cardName, isNeedSub) {
        this.cardsMng.operateCard(cardName, 1, 'add'); //加一张牌
        var result = this._subTwo(this._checkDiffPair, isNeedSub); //result = false || Object
        this.cardsMng.operateCard(cardName, 1, 'sub'); //减一张牌
        return result;
    },

    /**查看手中的牌是否已经胡了 (注意：已经将最新拿到的牌计算进去了)
     * @private
     * @param {function} callback -- 必须，回调函数，
     * @param {Object} thisObj -- 可省略，callback中的this指向，默认指向cardsHu_pair对象
     * @param {boolean} isNeedSub --
     * @returns {false|Object} -- false |
     *                      [ //同样是以w2为将牌，可以有好几种不同的胡牌方法, 但是本算法中，对于一种将牌，只检查一次，只要能胡，就会立即检查下一对将牌
     *                         //所以下面不会出现 jiangCard 相同的情况
                                [{name: 'e', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w2' ,
                                [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 'w8' ,
                                [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 't5' ,
                            ]
    */
    _subTwo: function _subTwo(callback, isNeedSub, thisObj) {
        //检查一整手牌是否可能会胡， 如果不能胡，返回false, 否则返回胡牌的数组
        var calcHuArr = []; //只是内部使用，记录本次判断的结果
        thisObj = thisObj || this;
        var subNum = isNeedSub ? 2 : 0;

        //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
        this.cardsMng.forEach(function (data) {
            if (data.num >= subNum) {

                data.arr[data.j] -= subNum; //去掉将牌
                console.log('_subTwo-start/////////////////////////////////去掉将牌后，手中牌是：' + this.cardsMng.getAllCardsStr());
                var huArr = [];
                //console.log('开始： ' + this.getCardNameFromCoord(i, j) + result);

                //result = false || [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}] //result中存了所有能正确组合的牌
                var result = callback.call(thisObj, huArr); //result = true 或 result = false
                //console.log('完成： ' + this.getCardNameFromCoord(i, j) + result);

                data.arr[data.j] += subNum; //加上先前去掉的将牌

                console.log('_subTwo-将牌和结果是：' + data.cardName + '  ', result);
                console.log('_subTwo-huArr: ', huArr);

                console.log('_subTwo-end/////////////////////////////////加上将牌后，手中牌是：' + this.cardsMng.getAllCardsStr());
                if (result === false) {
                    //不能胡牌， 注意，只有 === false时，才不能胡，如果是null 或 undefined 还是有可能胡
                    console.log('-----false----');
                } else {
                    //可以胡牌
                    console.log('-----true----');
                    huArr.push({ 'name': data.cardName, num: 2 }); //{'name': 'e' , 'num' : 3}
                    huArr.jiangCard = data.cardName;
                    //huArr = [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}, {name: 'w2', num: 2}].jiangCard = 't6'
                    calcHuArr.push(huArr);
                }
            }
        }, this);

        console.log('_subTwo-检查是否胡牌的结果 calcHuArr: ', calcHuArr);
        if (calcHuArr.length !== 0) {
            return calcHuArr;
        } else {
            return false;
        }
    },

    /**对于不同的将牌， 检查一整手牌是否可能会胡
     * @private
     * @param {Array} huArr -- 是一个数组，会更改它的值, 用于记录正确的组合
     *                         例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _checkDiffPair: function _checkDiffPair(huArr) {
        var result = true;
        var self = this;

        //data = { typeName: 't',j: 1, arr:[...], cardsHand: [...] }
        this.cardsMng.outerForEach(function (data) {
            /*当 data.arr=[0, 1, 0, 1] --风牌的数组,  当data.arr=[0, 1, 0] --箭牌的数组 , 当data.arr=[0, 1, 0, 1, 3, 1, 1, 0, 1] --序数牌的数组 */
            result = self._checkAllCards(data.arr, data.typeName, huArr); //result = true 或 result = false
            console.log('_checkDiffPair-传入_checkAllCards的数据是：', data.arr, data.typeName, data.j);
            //debugger;
            console.log("_checkDiffPair-本次检查的是" + data.typeName + "牌, 结果是：" + result);
            if (result === false) {
                return 'break'; //退出循环
            }
        });
        return result;
    },

    /**判断一组风牌 或 箭牌 或 序数牌 有没有可能会胡牌
     * @private
     * @param {Array} arr -- 当其值为 [0, 1, 0, 1] --风牌的数组,  当其值为 [0, 1, 0] --箭牌的数组 , 当其值为 [0, 1, 0, 1, 3, 1, 1, 0, 1] --序数牌的数组
     * @param {string} typeName -- 取值为 'f'(风牌) | 'j'(箭牌) | 'o'(筒牌) | 't'(条牌) | 'w'(万牌)
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _checkAllCards: function _checkAllCards(arr, typeName, huArr) {
        console.log('_checkAllCards-分割前的传入的3个参数是: ', arr, typeName, huArr);
        var result = true,
            splitAll = null;
        if (typeName === 'f' || typeName === 'j') {
            //如果是风牌、箭牌
            /*
                当传入arr = [0, 0, 1, 0, 3, 2, 0, 0 ] ==》 splitAll = [1, 3, 2]并具有属性： .index0 = 2, .index1 = 4, .index2 = 5
            */
            splitAll = TOOL.getNoZeroArr(arr); //去掉中间及两边的0元素
            console.log('_checkAllCards-分割(去零)的结果splitAll: ', splitAll);
            result = this._checkAllCard(splitAll, typeName, huArr); //result = true 或 result = false
            console.log('_checkAllCards-对于本次分割的计算结果result: ', result);
            if (result === false) {
                return false;
            }
        } else {
            /*   当传入arr = [0, 2,1,2,0, 0, 1,1,1,2] ==》splitAll = [ [2,1,2], [1,1,1,2] ]，这个数组中每个元素都具有属性： .index0 = 2, .index1 = 4, .index2 = 5
            *    当传入 arr = [1,1,1,2] ==》 splitAll =[ [1,1,1,2] ]，这个数组中每个元素都具有属性： .index0 = 2, .index1 = 4, .index2 = 5
            *    当传入arr = [0,0,0,0,0] ==》 splitAll =[]， 这个数组中没有元素
            */
            splitAll = TOOL.getSplitAll(arr); // 对arr进行分割
            console.log('_checkAllCards-分割(真的分割)的结果splitAll: ', splitAll);

            for (var i = 0; i < splitAll.length; i++) {
                /*
                    splitAll[i] = [1,1,1,2]
                */
                console.log('_checkAllCards-对于本次分割的片段' + i + ': ', splitAll[i], typeName, huArr);
                result = this._checkAllCard(splitAll[i], typeName, huArr);
                console.log('_checkAllCards-对于本次分割的片段: ' + i + '的计算结果result是: ', result);
                console.log('_checkAllCards-计算后的3个参数是: ', splitAll[i], typeName, huArr);
                if (result === false) {
                    return false;
                }
            }
        }
        return true;
    },

    /**恰好有一种牌时
     * @private
     * @param {Array} arr -- 长度为1的数组  如 [1], 且这个数组的元素一定不为0， 这个数组具有属性： .index0 = 2， 指定在分割之前该元素在原数组中的位置
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌 否则就是序数牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _justHasOneCard: function _justHasOneCard(arr, typeName, huArr) {
        if (arr[0] === 3) {
            //对应牌型： w2: 3  --- 即 w2有3张
            huArr.push({ 'name': this.cardsMng.getCardName(typeName, arr.index0), num: arr[0] }); //{'name': 'e' , 'num' : 3}
            return true;
        } else {
            return false;
        }
    },

    /**恰好有二种牌时
     * @private
     * @param {Array} arr -- 长度为2的数组  如 [1, 3], 且这个数组的元素一定不为0， 这个数组具有属性： .index0 = 2，.index1 = 3 指定在分割之前该元素在原数组中的位置
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌 否则就是序数牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _justHasTwoCard: function _justHasTwoCard(arr, typeName, huArr) {
        if (arr[0] === 3 && arr[1] === 3) {
            //对应牌型： w2: 3 ,w3: 3  --- 即 w2有3张，w3万有3张
            huArr.push({ 'name': this.cardsMng.getCardName(typeName, arr.index0), num: arr[0] });
            huArr.push({ 'name': this.cardsMng.getCardName(typeName, arr.index1), num: arr[1] });
            return true;
        } else {
            return false;
        }
    },

    /**恰好有三种牌时
     * @private
     * @param {Array} arr -- 长度为3的数组  如 [1, 3, 2], 且这个数组的元素一定不为0， 这个数组具有属性： .index0 = 2，.index1 = 3, .index2 = 4 指定在分割之前该元素在原数组中的位置
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌 否则就是序数牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _justHasThreeCard: function _justHasThreeCard(arr, typeName, huArr) {
        var name, name1, name2;

        //如果 中发白三个不一样的不算作一个顺子
        if (!this.isZFBOrder && typeName === 'j') {
            //如果是箭牌
            if (3 === arr[0] && 3 === arr[1] && 3 === arr[2]) {
                //对于中发白，如果三种都有，就只能都是3张，才能胡
                name = this.cardsMng.getCardName(typeName, arr.index0); //牌的名字 如： 'e'
                name1 = this.cardsMng.getCardName(typeName, arr.index1);
                name2 = this.cardsMng.getCardName(typeName, arr.index2);
                huArr.push({ 'name': name, 'name1': name1, 'name2': name2, num: arr[0] });
                return true;
            } else {
                //除了以上这种情况，其它情况下，都不可能胡牌
                return false;
            }
        } else {
            if (arr[0] === arr[1] && arr[0] === arr[2]) {
                //对应牌型： w2: n ,w3:n ,w4: n --- 即 w2有n张，w3万有n张， w4万有n张
                name = this.cardsMng.getCardName(typeName, arr.index0); //牌的名字 如： 'e'
                name1 = this.cardsMng.getCardName(typeName, arr.index1);
                name2 = this.cardsMng.getCardName(typeName, arr.index2);
                huArr.push({ 'name': name, 'name1': name1, 'name2': name2, num: arr[0] });
                return true;
            } else if ((arr[0] === 1 || arr[0] === 4) && (arr[1] === 1 || arr[1] === 4) && (arr[2] === 1 || arr[2] === 4)) {
                //对应牌型： w2: 1|4 ,w3: 1|4 ,w4: 1|4 --- 即 w2有1张，w3万有4张， w4万有1张
                name = this.cardsMng.getCardName(typeName, arr.index0); //牌的名字 如： 'e'
                name1 = this.cardsMng.getCardName(typeName, arr.index1);
                name2 = this.cardsMng.getCardName(typeName, arr.index2);

                huArr.push({ 'name': name, 'name1': name1, 'name2': name2, num: arr[0] });

                if (arr[0] === 4) {
                    huArr.push({ 'name': name, num: arr[0] - 1 });
                }
                if (arr[1] === 4) {
                    huArr.push({ 'name': name1, num: arr[1] - 1 });
                }
                if (arr[2] === 4) {
                    huArr.push({ 'name': name2, num: arr[2] - 1 });
                }
                return true;
            } else {
                //除了以上两种情况，其它情况下，都不可能胡牌
                return false;
            }
        }
    },

    /**恰好多余三种牌(四张及以上)时，要进行降级(所谓降级是指：将牌中的能够连起来的3张牌或6张牌去掉，最终使得牌数少于4张)
     * @private
     * @param {Array} arr -- 长度为>=4的数组  如 [1, 3, 2, 5], 且这个数组的元素一定不为0， 这个数组具有属性： .index0 = 2，.index1 = 3, .index2 = 4，.index3=5, ... 指定在分割之前该元素在原数组中的位置
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌 否则就是序数牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示降级成功，可能还要再次降级
    */
    _justHasOverThreeCard: function _justHasOverThreeCard(arr, typeName, huArr) {
        var result;
        console.log('_justHasOverThreeCard-降级前的3个参数：', arr, typeName, huArr);

        if (typeName === 'f' || typeName === 'j') {
            //如果是风牌、箭牌
            result = this._wordDegradeArr(arr, typeName, huArr); //与序数牌的降级方式不一样 result == true | false
        } else {
            result = this._degradeArr(arr, typeName, huArr); //调用序数牌的降级方法进行降级 result == true | false
        }

        console.log('_justHasOverThreeCard-降级计算的返回结果：', result);
        console.log('_justHasOverThreeCard-降级后的3个参数：', arr, typeName, huArr);

        if (result === false) {
            return false;
        } else {
            if (typeName === 'f' || typeName === 'j') {
                //如果是风牌、箭牌
                //当传入arr= [0, 0, 1, 0, 3, 2, 0, 0 ] ==》 [1, 3, 2]并具有属性： .index0 = 2, .index1 = 4, .index2 = 5
                arr = TOOL.getNoZeroArr(arr); //注意去中间0后，不改变原数组，所以必须赋值
            } else {
                //如果是序数牌
                //当传入arr= [0, 0, 1, 0, 3, 2, 0, 0 ] ==》 [ 1, 0, 3, 2, 0, 0]并具有属性： .index0 = 2, .index1 = 4, .index2 = 5
                TOOL.trimFrontZero(arr); //只去前面的0
            }
            console.log('_justHasOverThreeCard-去掉0后的数组是：', arr);
        }
    },

    /**判断这一个数组片断有没有可能胡牌
     * @private
     * @param {Array} arr -- 一个不含0的数组 如 [1,2,1] 而不是[1,0,1]， 当typeName 为 'f'或'j'时，这个数组具有属性： .index0 = 2, .index1 = 4, .index2 = 5
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _checkAllCard: function _checkAllCard(arr, typeName, huArr) {
        var result = true;
        console.log('_checkAllCard-' + typeName + "牌是：", arr);

        //如果 arr是空数组，表示通过检测
        while (arr.length !== 0) {
            switch (arr.length) {
                case 1:
                    //恰好有一种牌时
                    result = this._justHasOneCard(arr, typeName, huArr);
                    //console.log(arr, typeName,);
                    //console.log(11,result);
                    if (result === false) {
                        //console.log(11);
                        return false;
                    } else {
                        return true;
                    }
                case 2:
                    //恰好有二种牌时
                    result = this._justHasTwoCard(arr, typeName, huArr);
                    //console.log(arr, typeName,);
                    // console.log(22,result);
                    if (result === false) {
                        //console.log(22);
                        return false;
                    } else {
                        return true;
                    }

                case 3:
                    //恰好有三种牌时
                    result = this._justHasThreeCard(arr, typeName, huArr);

                    if (result === false) {
                        //console.log(33);
                        return false;
                    } else {
                        return true;
                    }

                case 4: //恰好多余三种牌时
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    //result = false表示没有通过检测，可以直接退出; result = true表示降级成功，可能还要再次降级, 所以不能退出
                    result = this._justHasOverThreeCard(arr, typeName, huArr);
                    if (result === false) {
                        //console.log(44);
                        return false;
                    } //注意，就算result 是 true，也不能在这里用return , 因为还要进行降级后的再次调用，此时不能退出循环

            }
        }
        if (result === false) {
            return false;
        } else {
            return true;
        }
    },

    /**字牌的降级，将四种牌转换为三种牌
     * @private 实际上这里只可能有一种情况
     * @param {Array} arr -- 长度为==4的数组  如 [1, 3, 2, 2], 且这个数组的元素一定不为0， 这个数组具有属性： .index0 = 2，.index1 = 3, .index2 = 4，.index3=5, ... 指定在分割之前该元素在原数组中的位置
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌 否则就是序数牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示降级成功，可能还要再次降级
    */
    _wordDegradeArr: function _wordDegradeArr(noZeroArr, typeName, huArr) {
        var val = 0,
            //必须同时减去的数量
        num = 0; //用于计数
        switch (noZeroArr[0]) {//牌型： 只看左边第一张牌的数量 如：e 有3张，则对应 case 3:
            case 0: //一张牌也没有时, 可能胡牌 对应： noZeroArr = [0, 1, 2,...] 可能胡牌
            case 3:
                //三张牌时, 可能胡牌 对应： noZeroArr = [3, 1, 2,...] 可能胡牌
                noZeroArr[0] = 0;
                return true; //0张或3张就通过检测
            case 1: // [1, 1, 2, 1] 所以必须 其中3个同时减去1 故 val = 1
            case 2:
                //一张牌或两张牌时, 可能胡牌 对应： noZeroArr = [2, 1, 2,...]  所以必须 其中3个同时减去2 故 val = 2
                val = noZeroArr[0];
                break;
            case 4:
                // [4, 1, 2, 1] 所以必须 其中3个同时减去1 故 val = 1
                val = 1;
                break;
        }
        // [1|4, 1|4, 1|4] [2,2,2]
        var nameObj = {};
        for (var i = 1; i < 4; i++) {
            //因为一定会有4个元素，且每个元素也一定不会是0 , 这样循环来减掉 val
            if (noZeroArr[i] >= val) {
                // 后面的三个数必须不小于val, 才有可能胡牌（否则的话，一减就成负数了）
                if (num <= 2) {
                    //计数，当为1时，表示已经减掉一个，必须要减够2个(第0个一定是能减够的)，才有可能胡
                    noZeroArr[i] -= val;
                    num++;
                    nameObj['name' + num] = this.cardsMng.getCardName(typeName, noZeroArr['index' + i]); //在这里设置 name1 name2
                    nameObj['num'] = val;
                }
            }
        }

        if (num !== 2) {
            //一共有3个才有可能胡牌
            return false;
        } else {
            nameObj['name'] = this.cardsMng.getCardName(typeName, noZeroArr['index0']);
            if (noZeroArr[0] === 4) {
                //如果第一张有4张牌
                huArr.push({ 'name': this.cardsMng.getCardName(typeName, noZeroArr['index0']), num: 3 });
            }
            huArr.push(nameObj);
            noZeroArr[0] = 0;
            return true;
        }
    },

    /**序数牌的降级，每次从数组中去掉元素，使元素越来越少
     * @private
     * @param {Array} arr -- 长度为>=4的数组  如 [1, 3, 2, 5], 且这个数组的元素一定不为0， 这个数组具有属性： .index0 = 2，.index1 = 3, .index2 = 4，.index3=5, ... 指定在分割之前该元素在原数组中的位置
     * @param {string} typeName -- 取值为 'f' | 'j' | 分别表示 风牌 | 箭牌 否则就是序数牌
     * @param {Array} huArr -- 是一个数组，会更改它的值， 例如： [{name: 's', num: 3}, {name: 't2', name1: 't3', 'name2': 't4'}]
     * @returns {boolean} -- false表示没有通过检测, true表示通过检测
    */
    _degradeArr: function _degradeArr(arr, typeName, huArr) {
        console.log('_degradeArr-序数牌的降级前的三个参数：', arr, typeName, huArr);

        switch (arr[0]) {//牌型： 只看左边第一张牌的数量 如：w2万有3张，则对应 case 3:
            case 0:
                //一张牌也没有时, 可能胡牌 对应 arr=[0, 1, 1, ...]
                return true;
            case 1:
                //恰好有一张牌时 对应 arr=[1, 2, 2, ...], 这样就可以去掉3张牌 从而变成 arr = [0, 1, 1,...]
                arr[0] = 0;arr[1]--;arr[2]--;
                if (arr[1] < 0 || arr[2] < 0) {
                    //arr = [0, -1, 1,...], 这样一定不能胡牌
                    return false;
                } else if (arr[2] === 0) {
                    // arr = [0, 1|2|4, 0,...], 这样一定不能胡牌
                    if (arr[1] !== 3 && arr[1] !== 0) {
                        return false;
                    } else {
                        if (arr[1] === 3) {
                            //保证数组中间不会出现0元素
                            arr[1] = 0;
                            huArr.push({ 'name': typeName + (arr.index1 + 1), num: 3 });
                        }
                    }
                } else {
                    //否则就可能胡牌
                    huArr.push({ 'name': typeName + (arr.index0 + 1),
                        'name1': typeName + (arr.index1 + 1),
                        'name2': typeName + (arr.index2 + 1),
                        num: 1 });
                }

                break;
            case 2:
                //恰好有二张牌时 对应 arr=[2, 2, 2, ...],
                arr[0] = 0;arr[1] -= 2;arr[2] -= 2;
                if (arr[1] < 0 || arr[2] < 0) {
                    //arr = [0, -1, 1,...], 这样一定不能胡牌
                    return false;
                } else if (arr[2] === 0) {
                    //保证数组中间不会出现0元素 arr = [0, 1|2|4, 0,...], 这样一定不能胡牌
                    if (arr[1] !== 3 && arr[1] !== 0) {
                        return false;
                    } else {
                        if (arr[1] === 3) {
                            //保证数组中间不会出现0元素
                            arr[1] = 0;
                            huArr.push({ 'name': typeName + (arr.index1 + 1), num: 3 });
                        }
                    }
                } else {
                    //否则就可能胡牌
                    huArr.push({ 'name': typeName + (arr.index0 + 1),
                        'name1': typeName + (arr.index1 + 1),
                        'name2': typeName + (arr.index2 + 1),
                        num: 2 });
                }

                break;
            case 3:
                //恰好有三张牌时 对应 arr=[3, 2, 2, ...], 可能胡
                arr[0] = 0; //直接去掉3张就是了
                huArr.push({ 'name': typeName + arr.index0, num: 3 });
                break;
            case 4:
                //恰好有四张牌时 对应 arr=[4, 2, 3, ...],
                arr[0] = 0;arr[1] -= 1;arr[2] -= 1; // 去掉后，变成 arr=[0, 1, 2, ...],
                if (arr[1] < 0 || arr[2] < 0) {
                    //arr = [0, -1, 1,...], 这样一定不能胡牌
                    return false;
                } else if (arr[2] === 0) {
                    //保证数组中间不会出现0元素 arr = [0, 1|2|4, 0,...], 这样一定不能胡牌
                    if (arr[1] !== 3 && arr[1] !== 0) {
                        return false;
                    } else {
                        if (arr[1] === 3) {
                            //保证数组中间不会出现0元素
                            arr[1] = 0;
                            huArr.push({ 'name': typeName + (arr.index1 + 1), num: 3 });
                        }
                    }
                } else {
                    //否则就可能胡牌
                    huArr.push({ 'name': typeName + (arr.index0 + 1),
                        'name1': typeName + (arr.index1 + 1),
                        'name2': typeName + (arr.index2 + 1),
                        num: 1 });
                    huArr.push({ 'name': typeName + (arr.index0 + 1), num: 3 });
                }
        }
        console.log('_degradeArr-序数牌的降级后的三个参数：', arr, typeName, huArr);
        return true; //如果不是false, 就有可能胡牌
    }

});

module.exports = CardsHu_pair;

cc._RFpop();
},{}],"cardsMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '42dd72wYYlAwIY45Efmg7pg', 'cardsMng');
// scripts\room\model\cardsMng\cardsMng.js

'use strict';

var Combination = require('combination');

var OutCards = require('outCards'); //出牌
var OperateCards = require('operateCards'); //操作的牌
var HandCardsChild = require('handCardsChild'); //手牌

var CardsAll = require('cardsAll'); //综合
var OperateCards_operate = require('operateCards_operate'); //操作的牌
var OutCards_operate = require('outCards_operate'); //出的牌

var CalcHuMng = require('calcHuMng'); //计算教


/**
 * @class
 * @classdesc -- 管理自己出的牌
 */
var CardsMng = cc.Class({ //牌的信息管理
    name: 'CardsMng',
    extends: Combination,

    ctor: function ctor() {
        this.outCards = new OutCards();
        this.operateCards = new OperateCards();
        this.handCards = new HandCardsChild();

        this.operateCards_operate = new OperateCards_operate({
            handCards: this.handCards,
            operateCards: this.operateCards
        });

        this.outCards_operate = new OutCards_operate({
            outCards: this.outCards
        });

        this.cardsAll = new CardsAll({
            outCards: this.outCards,
            handCards: this.handCards,
            operateCards: this.operateCards,
            operateCards_operate: this.operateCards_operate
        });

        this.combination([{ name: 'handCardsChild', combineType: 'method', type: 'objectExist',
            config: { obj: this.handCards }
        }, { name: 'outCards_operate', combineType: 'method', type: 'objectExist',
            config: { obj: this.outCards_operate }
        },

        //  {name:'cardFace', combineType:'method', type:'objectExist',
        //     config:{ obj: this.cardFace }
        //  },

        { name: 'operateCards_operate', combineType: 'method', type: 'objectExist',
            config: { obj: this.operateCards_operate }
        }, { name: 'cardsAll', combineType: 'method', type: 'objectExist',
            config: { obj: this.cardsAll }
        }]);

        this.calcHuMng = new CalcHuMng({
            cardsMng: this
        });

        this.combination([{ name: 'calcHuMng', combineType: 'method', type: 'objectExist',
            config: { obj: this.calcHuMng }
        }]);
    },

    reset: function reset() {
        console.log('cardsMng.js重置了。');
        this.reset_other();
        this.outCards.reset(); //重置出的牌
        this.operateCards.reset(); //重置操作的牌
    }
});

module.exports = CardsMng;

cc._RFpop();
},{"calcHuMng":"calcHuMng","cardsAll":"cardsAll","combination":"combination","handCardsChild":"handCardsChild","operateCards":"operateCards","operateCards_operate":"operateCards_operate","outCards":"outCards","outCards_operate":"outCards_operate"}],"combination":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f190bH8lFBB/pUh8czJvvEE', 'combination');
// scripts\common\combination.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * @class
 * @classdesc -- 用于组合类或对象的类，也可以作为基类
 *
 * 作为基类使用：如果在子类中定义了 与 要组合的类中的方法相同的方法， 就只会调用子类的方法，而其它方法命名为 子类方法 + '_other'
 *              如果没在子类中定义相同方法，就会创建一个这个方法，以此来调用所有组合类中的相同方法
 *
 * 作为一个组合类使用(即直接new)： 就会创建一个这个方法，以此来调用所有组合类中的相同方法
 */
var Combination = cc.Class({
    name: 'Combination',

    /**
     * @constructor
     */
    ctor: function ctor() {
        this.data = []; //用户数据的存储(不包括自己)
        this.methodObj = {}; //{reset: [obj1, obj2], display: [obj1] } //注意，并不包括this
        this.methodLen = 0; //记录最新的已加入的方法的总数
        this.oldMethodLen = this.methodLen; //上一次的方法的总数(即本次加入前有多少个方法)
    },

    /**
     * 移除几个以前添加进来的类或对象
     * @public
     * @param {Array} config --
     *  [ 'userInfo',  'userState', ]
     */
    remove: function remove(config) {
        var arr = config;
        for (var i = 0; i < arr.length; i++) {
            for (var j = 0; j < this.data.length; j++) {
                if (this.data[j].name === arr[i]) {
                    this.data.splice(j, 1); //删除
                    j--;
                }
            }
            for (var prop in this.methodObj) {
                var arr1 = this.methodObj[prop];
                for (var j = 0; j < arr1.length; j++) {
                    if (arr1[j].name === arr[i]) {
                        arr1.splice(j, 1); //删除
                        j--;
                    }
                }
                if (arr1.length === 0) {
                    //如果被删空了
                    if (prop.search(/_other$/) === -1) {
                        //如果方法名不是_other
                        delete this[prop]; //删除本对象上的方法
                    }
                    delete this.methodObj[prop];
                }
            }
        }
        return this;
    },

    /**
     * 添加几个类进来, 然后就可以通过本类对象，直接调用其它类的方法
     * @public
     * @param {Array} config --
     *  [
     *    { name: 'userInfo', combineType: 'data', type: 'class', config: {}}, //注意name的值表示 userInfo.js
     *    { name: 'userState',combineType: 'data method', type: 'object', config: {}}, //如果是对象, 不会用到config
     * ]
     */
    combination: function combination(config) {
        if (!(config && config.length)) {
            return;
        }

        for (var i = 0; i < config.length; i++) {
            //创建组合的类的对象
            var obj = null;
            switch (config[i].type) {
                case 'class':
                    this.className = require(config[i].name);
                    if (config[i].config !== undefined) {
                        obj = new this.className(config[i].config); //创建一个对象
                    } else {
                        obj = new this.className(); //创建一个对象
                    }
                    break;
                case 'object':
                    obj = require(config[i].name); //不会创建对象
                    break;
                case 'objectExist':
                    obj = config[i].config.obj; //不会创建对象
                    break;
            }

            obj.name = config[i].name;

            if (config[i].combineType.search(/data/) !== -1) {
                // console.log('添加了数据 ');
                this.data.push(obj);
            }

            if (config[i].combineType.search(/method/) !== -1) {
                // console.log('添加了方法 ');
                this.oldMethodLen = this.methodLen; //记录下加入方法前的总个数(以便比较有没有增加新的方法)
                this._changeMethodObj_(obj); //将一个对象的方法的数据存起来
                //console.log(this.oldMethodLen, this.methodLen);
                this.oldMethodLen !== this.methodLen && this._copyMethod_(); //只有加入的新的方法，才进行方法的复制
            }

            // console.log(this.methodObj);
        }
        return this;
    },

    /**
     * 访问或设置本对象的属性
     * 注意，对于没有事先定义好的属性，是不会有任何操作的，既查不到，也设置不了，均返回undefined
     * 示例： 传入 {openId: '99999, nickName: null }  ==》  {openId: '99999, nickName: 玩家10003 }
     *        attr(nickName, '玩家123') ==> 玩家123
     * @public
     * @param {string|Object} 当传入的是string时，attrName_obj -- 属性名, 'faceIcon'(头像)，'nickName'(昵称)，'openId'(openId )，
     *                                          'userId'(用户ID)，'roomCards'(房卡数)，'joinOrder'(加入房间的序号)，
     *                                          'positionOrder'(自己在房间中的位置号)
     *                       当传入的是Object时，示例：传入 {openId: '99999, nickName: null }  ==》  {openId: '99999, nickName: 玩家10003 }
     * @param {any} value -- 可省略，要设置的属性值，如果省略，表示获取属性值，否则表示设置属性值
     * @returns {any|undefined} -- 如果传入了两参数，就会返回对应属性的值，
     *                              如果只传入了一个参数，且是一个对象，就取值或设置值
     */
    attr: function attr(attrName_obj, value) {
        if (!attrName_obj) {
            return;
        }
        if ((typeof attrName_obj === 'undefined' ? 'undefined' : _typeof(attrName_obj)) === 'object') {
            for (var prop in attrName_obj) {
                attrName_obj[prop] = this._attr_(prop, attrName_obj[prop]);
            }
            return attrName_obj;
        } else {
            return this._attr_(attrName_obj, value);
        }
    },

    /**
     * 通过一个对象，访问或设置本对象的属性
     *
     * 示例：attr(nickName) ==> '玩家10003'     attr(nickName, '玩家123') ==> undefined
     * @private
     * @param {string} attrName --属性名, 'faceIcon'(头像)，'nickName'(昵称)，'openId'(openId )，
     *                                     'userId'(用户ID)，'roomCards'(房卡数)，'joinOrder'(加入房间的序号)，
     *                                      'positionOrder'(自己在房间中的位置号)
     * @param {any} value -- 可省略，要设置的属性值，如果省略，表示获取属性值，否则表示设置属性值
     * @returns {any|undefined} -- 如果设置或询查成功，会返回这个值，否则返回undefined
     */
    _attr_: function _attr_(attrName, value) {
        var data = this.data;
        for (var i = 0; i < data.length; i++) {
            if (data[i][attrName] !== undefined) {
                if (value === undefined) {
                    return data[i][attrName];
                } else {
                    return data[i][attrName] = value;
                }
            }
        }
    },

    /**
     * 将加入的类的对象方法复制至本对象
     * @private
     */
    _copyMethod_: function _copyMethod_() {
        // console.log('执行了函数 _copyMethod_');
        var funcName; //函数名
        for (var prop in this.methodObj) {

            if (this.methodObj[prop].selfHave) {
                //如果原本在this上定义了
                funcName = prop + '_other';
            } else {
                funcName = prop;
            }

            if (funcName in this) {
                continue;
            } //保证只定义一份

            this[funcName] = function (prop) {
                return function () {
                    //在this对象上创建相同的方法名，来调用其它对象上的相同的方法
                    //  console.log(arguments);
                    //debugger;

                    var arr = this.methodObj[prop];
                    for (var i = 0; i < arr.length; i++) {
                        //  console.log( arr[i]);
                        var result = arr[i][prop].apply(arr[i], arguments);
                        if (result !== undefined) {
                            return result;
                        }
                    }
                    if (prop === 'reset') {
                        //执行数据对象中的重置方法
                        var arr = this.data;
                        for (var i = 0; i < arr.length; i++) {
                            if (!arr[i][prop]) {
                                continue;
                            }
                            var result = arr[i][prop].apply(arr[i], arguments);
                            if (result !== undefined) {
                                return result;
                            }
                        }
                    }
                };
            }(prop);
        }
        // console.log(this);
    },

    /**
     * 将一个对象的方法的数据存起来
     * @private
     */
    _changeMethodObj_: function _changeMethodObj_(obj) {
        for (var prop in obj) {
            //名字不以_开头或结尾的那些方法，才会被存储
            if (typeof obj[prop] === 'function' && prop !== 'constructor' && !prop.match(/(^_)|(_$)/)) {

                if (prop in this.methodObj) {
                    //如果已经有了
                    this.methodObj[prop].push(obj);
                } else {
                    this.methodObj[prop] = [obj]; //如果还没有，就创建一个数组来存入
                    this.methodLen++; //总数加1

                    if (prop in this) {
                        //如果this上有了
                        this.methodObj[prop].selfHave = true;
                    }
                }
            }
        }
    }

});

module.exports = Combination;

cc._RFpop();
},{}],"conditions":[function(require,module,exports){
"use strict";
cc._RFpush(module, '239faMVjHxO67OQK9iL+Klo', 'conditions');
// scripts\common\msgMng\conditions.js

'use strict';

//这些消息必须在打牌场景加载完成后才能执行（因为在rebuild的时候，场景还没加载出来，别人就点了出牌消息，此时自己就处理不了）
var condition = {

    //房间加载完成才能处理这些消息
    roomFinish_condition: ['juScore', 'roomScore', 'word', 'face', 'ready', 'dismissing', 'dismiss', 'leaveLine', 'reConnect'],

    joinOver_condition: ['start', 'napai'],

    buHuaOver_condition: ['outCard', 'guo', 'pong', 'hu', 'mingGang', 'chi', 'buGang', 'anGang'],

    //存在房间，并且房间中有指定的用用户
    existRoomUser_condition: ['leaveLine', 'reConnect', 'exitRoom'],

    //总结算页面，必须等用户点击单局结算页面中的继续按钮后，才能弹出来
    allResultShow_condition: ['roomScore'],

    /**
     * 将本对象的信息复制到 msgCondition对象中
     */
    initConditions: function initConditions() {
        for (var prop in condition) {
            var arr = condition[prop];
            for (var i = 0; i < arr.length; i++) {
                if (typeof arr[i] === 'function') {
                    continue;
                }

                if (arr[i] in msgCondition) {
                    msgCondition[arr[i]].push(prop);
                } else {
                    msgCondition[arr[i]] = [prop];
                }
            }
        }
    }
};

/**
 * msgCondition = {
 *                  roomScore: ['allResultShow_Condition', 'roomFinish_condition'], ready: ['roomFinish_condition'],
 *                  allResultShow_condition: function(){...},  roomFinish_condition: function(){...},
 *                }
 */
var msgCondition = {

    /**
     * 房间加载完成
     */
    roomFinish_condition: function roomFinish_condition(msg) {
        return getGI('roomSceneFinished'); //room场景加载完成
    },

    joinOver_condition: function joinOver_condition() {
        return getGI('joinOver'); //加入了四个用用户 ，以后才能执行 start消息
    },

    buHuaOver_condition: function buHuaOver_condition() {
        return getGI('buHuaOver'); //等四个用户首次补花完成后 ，才能执行出牌消息
    },

    /**
     * 存在房间，并且房间中有指定的用用户
     */
    existRoomUser_condition: function existRoomUser_condition(msg) {
        var openId = msg.message;
        return window.room && openId && room.getUser(openId);
    },

    /**
     * 总结算页面，必须等用户点击单局结算页面中的继续按钮后，才能弹出来
     */
    allResultShow_condition: function allResultShow_condition(msg) {
        var isCanShowAllResult = room.attr('isCanShowAllResult');
        if (isCanShowAllResult) {
            room.attr('isCanShowAllResult', false);
            return true;
        }
        return isCanShowAllResult;
    }
};

condition.initConditions();

module.exports = msgCondition;

cc._RFpop();
},{}],"controllerMapMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7acb8VgEkxMWInmRYdBkq+8', 'controllerMapMng');
// scripts\common\msgMng\controllerMapMng.js

'use strict';

/**@class
 * @classdesc 用于调用各种消息
 */
var ControllerMapMng = cc.Class({
    name: 'ControllerMapMng',

    /**
     * @constructor
     * @param {Object} config -- {
     *      controllerMap: //必须, 控制器地图
     * }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.controllerMap = config.controllerMap;

        this.controllerSave = {}; //用来存储所有的控制器对象 格式为：消息名1: 对象
        this.controllerObjArr = []; //所有 的控制器对象
        this._init_(); //初始化
    },

    /**
     * 调用所有控制器对象上的reset方法进行重置
     * @public
     */
    resetController: function resetController() {
        var arr = this.controllerObjArr;
        for (var i = 0; i < arr.length; i++) {
            arr[i].reset && arr[i].reset();
        }
    },

    /**
     * 访问消息的控制器
     * @public
     * @param {Object} data -- 接收到的数据  如：{errMsg: string, type: string, message: some, success: boolean}
     */
    accessController: function accessController(data) {

        var msgType = data.type,
            //消息的类型名 如: 'join'  'anGang'
        controllerObj = data.success ? this.controllerSave[msgType] : this.controllerSave['error_controller'],
            //控制器对象
        indexController = controllerObj['index'],
            //控制器的主方法
        msgController = controllerObj[msgType + '_controller']; //消息的控制方法

        indexController && indexController.call(controllerObj, data); //调用主方法
        msgController && msgController.call(controllerObj, data); //调用控制方法

        if (!data.success && !msgController) {
            //如果失败了，并且没有专门的处理错误的控制器，就使用默认的方式
            console.log('---简单的输出错误信息---' + data.errMsg); //简单的输出错误信息
            socket.finishMsg('error_sample'); //标记本条消息处理完成
        }
    },

    /**
     * 创建控制器对象
     * @private
     */
    _init_: function _init_() {
        this.className; //类名
        for (var prop in controllerMap) {
            this.className = require(prop); //引入文件
            var controllerObj = new this.className(); //控制器对象
            controllerObj.fileName = prop; //控制器对象上 存储文件名

            this.controllerObjArr.push(controllerObj);

            //注意，错误控制对象 是经过特殊的处理
            if (prop === 'error_controller') {
                this.controllerSave['error_controller'] = controllerObj;
            } else {
                var msgNameArr = this.controllerMap[prop]; //消息名的数组
                for (var i = 0; i < msgNameArr.length; i++) {
                    this.controllerSave[msgNameArr[i]] = controllerObj;
                }
            }
        }
    }
});

module.exports = ControllerMapMng;

cc._RFpop();
},{}],"dialogWords":[function(require,module,exports){
"use strict";
cc._RFpush(module, '837c85N7OpC8aK3ha+B+xLt', 'dialogWords');
// scripts\common\dialogWords.js

'use strict';

/**
 * 聊天的默认文字
 */
var words = {
    data: {
        c00: '大家多多关照啊',
        c01: '牌好，心情就好',
        c02: '牌品好，人品才会好',
        c03: '看到没有，赌神就在这里啊',
        c04: '赶紧出牌啊',
        c05: '哈哈，风水轮流转呀',
        c06: '这一盘，看你们怎么办',
        c07: '都是自己人，何必呢',
        c08: '唉，怎么会这样呢',
        c09: '这两天手气怎么这么衰呢'
    },
    order: ['c00', 'c01', 'c02', 'c03', 'c04', 'c05', 'c06', 'c07', 'c08', 'c09']
};

module.exports = words;

cc._RFpop();
},{}],"dismissAlert_click":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'dafa78s0xhIn67icYFc02Sa', 'dismissAlert_click');
// scripts\room\view\alert\dismissAlert_click.js

'use strict';

/**
 * 解散弹出框上的事件 挂载于 Canvas/dismissAlert节点上
*/
cc.Class({
    extends: cc.Component,

    onLoad: function onLoad() {
        this.dismissAlert = room.dismissAlert; //this._test_(); //
    },

    //(测试用)
    _test_: function _test_() {
        var DismissAlert = require('dismissAlert_display');
        return new DismissAlert();
    },

    /**
     * 解散框的显示和隐藏
     * @public
     * @param {string} isShow -- 为'show'时，表示显示整个解散弹出框，否则隐藏（'hide'）
     */
    dismissAlertClick: function dismissAlertClick(event) {
        if (!room.attr('isCanDismiss')) {
            room.displayRoomInfo('onLine', '只有房间中的全部成员同时在线时，才能解散房间！');
            return;
        }
        room.dismissAlert.startDismiss(); //显示解散弹出框
    },

    /**
     * 同意解散时的回调函数
     * 1.挂载于 解散房间弹出框的同意按钮上
     * @public
     */
    agreeClick: function agreeClick(event, noVoice) {
        window.audio.playEffect('waterClick'); //播放点击声音

        //发送同意解散消息
        clientUser.send({ message: true, type: 'dismissing' });

        //禁用这两个按钮
        this.dismissAlert.changeBtnState('disabled');

        //显示  自己已同意
        this.dismissAlert.showOperate(clientUser.attr('nickName'), true);
    },

    /**
     * 不同意解散时的回调函数
     * 1.挂载于 解散房间弹出框的继续(不同意)按钮上
     * @public
     */
    continueClick: function continueClick() {
        window.audio.playEffect('waterClick'); //播放点击声音

        //发送不同意解散消息
        clientUser.send({ message: false, type: 'dismissing' });

        //禁用这两个按钮
        this.dismissAlert.changeBtnState('disabled');

        //显示 自己没同意
        this.dismissAlert.showOperate(clientUser.attr('nickName'), false);
    }

});

cc._RFpop();
},{"dismissAlert_display":"dismissAlert_display"}],"dismissAlert_display":[function(require,module,exports){
"use strict";
cc._RFpush(module, '4bf73YCBTNFer5EGJcG5FSn', 'dismissAlert_display');
// scripts\room\view\alert\dismissAlert_display.js

'use strict';

/**
 * @class
 * @classdesc 解散弹出框的显示
*/
var DismissAlert_display = cc.Class({
    name: 'DismissAlert_display',
    /**
    * @constructor
    */
    ctor: function ctor() {
        this.dismissNode = cc.find('Canvas/dimissAlert'); //弹出框节点
        this.contentNode = cc.find('alertContent/dikuang', this.dismissNode); //内容节点
        this.dimiss_Atlas = getGI('roomGlobalRes').dimiss_Atlas; //解散房间的图集
        //重置
        this.reset();
    },

    getIsShow: function getIsShow() {
        return this.isShow;
    },

    /**
     * 重置
     */
    reset: function reset() {
        for (var i = 0; i < 4; i++) {
            this.contentNode.getChildByName('check' + i).getComponent(cc.Sprite).spriteFrame = this.dimiss_Atlas.getSpriteFrame('waiting');
            this.contentNode.getChildByName('user' + i).acitve = false;
            this.contentNode.getChildByName('check' + i).acitve = false;
        }
        this.isShow = false;
        room.attr('dismissingNum', 0); //重置解散房间消息的数量为0
    },

    initUsers: function initUsers(startUser) {
        var user = void 0,
            isShow = void 0,
            node = void 0;
        this.contentNode.getChildByName('startPerson').getComponent(cc.Label).string = startUser.attr('nickName');
        for (var i = 0; i < 4; i++) {
            user = room.usersArr[i];
            isShow = user ? true : false;
            node = this.contentNode.getChildByName('user' + i);
            node.active = isShow;
            this.contentNode.getChildByName('check' + i).active = isShow;
            this.contentNode.getChildByName('check' + i).getComponent(cc.Sprite).spriteFrame = this.dimiss_Atlas.getSpriteFrame('waiting');
            if (isShow) {
                node.getComponent(cc.Label).string = user.attr('nickName');
            }
        }
    },


    /**
    * 解散框的显示和隐藏
    * @param {string} isShow -- 为'show'时，表示显示整个解散弹出框，否则隐藏（'hide'）
    * @param {number} delay -- 延迟多少毫秒再隐藏, 当第一个参数为show时，第二个参数表示是否需要重置 传入 'noReset'表示不要重置
    */
    dismissShow: function dismissShow(isShow, startUser) {
        var _this = this;

        this.isShow = isShow === 'show' ? true : false;

        if (this.isShow) {
            this.initUsers(startUser); //初始化用户显示
            this.changeBtnState('enabled'); //启用点击事件
            this.dismissNode.active = true; //显示弹出框
        } else {
            var time = startUser;
            if (time) {
                setTimeout(function () {
                    _this.dismissNode.active = false; //隐藏弹出框
                    _this.reset();
                }, time);
            } else {
                this.dismissNode.active = false; //隐藏弹出框
                this.reset();
            }
        }
    },

    /**
     * 发起解散的人首次调用
     */
    startDismiss: function startDismiss() {
        //发送解散消息
        clientUser.send({ message: true, type: 'dismissing' });

        this.isShow = true;
        this.initUsers(clientUser); //初始化用户显示
        //第一个人一定同意
        this.showOperate(clientUser.attr('nickName'), true);

        //显示弹出框
        this.dismissNode.active = true; //显示弹出框

        //对于自己，不能点击
        this.changeBtnState('disabled');
    },


    /**
     * 当点了一次后，禁用这两个按钮
     * @public
     * @param {boolean} isDisabled -- 取'enabled'时，启用这两个按钮，否则禁用（disabled）
     */
    changeBtnState: function changeBtnState(isDisabled) {
        isDisabled = isDisabled === 'enabled' ? true : false;
        this.contentNode.getChildByName('refuseBtn').getComponent(cc.Button).interactable = isDisabled;
        this.contentNode.getChildByName('agreeBtn').getComponent(cc.Button).interactable = isDisabled;
    },

    /**
     * 当别人选了同意或不同意时，自己这边要显示出来
     * @public
     * @param {string} nickName -- 表示谁的选择
     * @param {boolean} isAgree -- true表示同意解散，在页面上显示绿色
     * @param {number} i -- 可省略，位于第几个(0表示自己)
     */
    showOperate: function showOperate(nickName, isAgree) {
        //取得要设置的图片名称
        for (var i = 0; i < 4; i++) {
            var userNode = this.contentNode.getChildByName('user' + i);
            if (userNode.getComponent(cc.Label).string === nickName) {

                var checkNode = this.contentNode.getChildByName('check' + i);
                userNode.active = true;
                checkNode.getComponent(cc.Sprite).spriteFrame = this.dimiss_Atlas.getSpriteFrame(isAgree ? 'agree' : 'notAgree');
                checkNode.active = true;
                this.index++;
            }
        }
    }

});

module.exports = DismissAlert_display;

cc._RFpop();
},{}],"dismiss_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6c3c4VhzG5B151VdYVOkJ+0', 'dismiss_controller');
// scripts\common\controller\messageController\dismiss_controller.js

'use strict';

/**@class
 * @classdesc 解散和准备
 */
var Dismiss_controller = cc.Class({
    name: 'Dismiss_controller',

    ctor: function ctor() {
        var config = arguments[0];
        this.disimssNum = 0; //统计同意解散的人数
    },

    /**
     * 准备消息，只会收到三条这样的消息，第四个人点了准备后，直接发的是start消息
     * data: {  e | errMsg: null,
                m | message: ["10004", '38992', '389928'],
                s | success: true, t | type: 2, ready
            }
     */
    ready_controller: function ready_controller(data) {
        console.log('执行 ready_controller...');

        room.attr('readyArr', data.message); //存起来， 因为在单局结算页面，如果用户不点继续，就不会显示ok，直到用户点了之后，才开始显示
        var readyArr = data.message;

        if (room.attr('period') !== 'endJu') {
            //如果是在还没发牌之前
            //如果数组中最后一个人是自己，就说明自己是第一次收到ready消息
            for (var i = 0; i < readyArr.length; i++) {
                //显示其它人的ok
                room.getUser(readyArr[i]).displayHead('ok', 'show'); //显示ok状态;
            }
        } else {
            //如果是单局结束, 这里显示以后准备好的，在自己之前准备好的人是在点击继续游戏的时候设置的。
            //只有当自己的单局结算页面隐藏了以后，才会显示是否是ok状态
            if (!room.singleResult.isSingleAlertShow()) {
                for (var i = 0; i < readyArr.length; i++) {
                    //显示其它人的ok
                    room.getUser(readyArr[i]).displayHead('ok', 'show'); //显示ok状态;
                }
            } else {
                //如果还在显示单局结算界面，其它人准备了就提示出来
                room && room.displayRoomInfo && room.displayRoomInfo('onLine', room.getUser(readyArr[readyArr.length - 1]).attr('nickName') + ' 继续了游戏...');
            }
        }

        socket.finishMsg('ready'); //标记本条消息处理完成
    },

    /** 解散消息
     *data: {  e | errMsg: null,
                m | message: {m:true, p:10004 , t: 31},
                s | success: true, t | type: 4, dismissing
            }
     */
    dismissing_controller: function dismissing_controller(data) {
        console.log('客户端-- 执行 dismissing_controller...');
        console.log(data);
        room.attr('dismissingNum', room.attr('dismissingNum') + 1);
        var message = data.message;

        if (!room.msgIsMeSend(message.p)) {

            if (!room.dismissAlert.getIsShow()) {
                //如果还没弹出
                //重置解散弹出框
                room.dismissAlert.reset();
                //显示解散弹出框
                room.dismissAlert.dismissShow('show', room.getUser(message.p));
            }

            //设置发消息的人是否同意
            room.dismissAlert.showOperate(room.getUser(message.p).attr('nickName'), message.m);
        }

        if (room.attr('dismissingNum') > 3) {
            //接收到四条消息后， 才可能显示总结算界面
            room.attr('dismissingNum', 0);
            room.attr('isCanShowAllResult', true);
        }

        socket.finishMsg('dismissing'); //标记本条消息处理完成
    },

    /**
     * 发送四条dismissing消息后，会收到dismiss解散消息，
     * 如果还没开始打牌就解散成功，会收到dismiss消息，但是如果有结算数据，就不会收到该消息，而是直接收到 roomScore消息，进行总结算
     * data : {  e | errMsg: null,
                m | message: true,
                s | success: true, t | type: 30, "dismiss"
            }
     */
    dismiss_controller: function dismiss_controller(data) {
        console.log('客户端-- 执行 dismiss_controller...');

        if (data.message) {
            //如果全部同意解散

            setTimeout(function () {
                cc.director.loadScene('hall', function () {
                    TOOL.backHall();
                    socket.finishMsg('dismiss'); //标记本条消息处理完成
                });
            }, 1000);
        } else {
            //隐藏解散弹出框
            room.dismissAlert.dismissShow('hide', 1000);
            socket.finishMsg('dismiss'); //标记本条消息处理完成
        }
    }

});

module.exports = Dismiss_controller;

cc._RFpop();
},{}],"error_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7ee24iwWN5J1pZsbs2fe7br', 'error_controller');
// scripts\common\controller\messageController\error_controller.js

'use strict';

/**@class
 * @classdesc 所有的错误消息
 */
var Error_controller = cc.Class({
    name: 'Error_controller',

    //加入房间出错消息
    join_controller: function join_controller(data) {
        getGI('hall').errorReminder(data.errMsg); //房间号不存在 房间已满
        socket.finishMsg('error_join'); //标记本条消息处理完成
        getGI('hall').loading('end'); //加载完成
    },

    ready_controller: function ready_controller(data) {
        console.log('ready出错了....');
        socket.finishMsg('error_ready'); //标记本条消息处理完成
    },

    create_controller: function create_controller(data) {
        getGI('hall').errorReminder(data.errMsg); //房间号不存在 房间已满
        socket.finishMsg('error_create'); //标记本条消息处理完成
        getGI('hall').loading('end'); //加载完成
    },

    error_controller: function error_controller(data) {
        console.log('error_controller出错了....', data);
        if (window.room) {
            cc.director.loadScene('hall', function () {
                window.reset();
                //显示用户的信息
                getGI('hall').display({
                    nickName: clientUser.attr('nickName'), //昵称
                    userId: clientUser.attr('userId'), //用户Id
                    headIcon: clientUser.attr('faceIcon'), //头像图标
                    roomCardNum: clientUser.attr('roomCards') });
                socket.finishMsg('error_error'); //标记本条消息处理完成
            });
        } else {
            TOOL.createSocket({ type: 'login', message: window.userInfo }); //如果没有打开过socket就打开一个socket
            socket.finishMsg('error_error'); //标记本条消息处理完成
        }
    }

});

module.exports = Error_controller;

cc._RFpop();
},{}],"globalData":[function(require,module,exports){
"use strict";
cc._RFpush(module, '2457chhis1HrL5nG58XN72I', 'globalData');
// scripts\common\globalData.js

'use strict';

/**
http://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0c15c83d5c0f91f&redirect_uri=http://www.uitrs.com/quanzhou/qzmj&response_type=code&scope=snsapi_userinfo&state=wx
 */
var myGlobal = {
    debug: 1, //调试用 0 表示不进行页面调试 1表示在页面中显示调试信息
    model: 'dev', //'produce' 或 'dev'，dev为开发模式，produce为生产模式
    isBuildUserInfo: true, //是否自己构建用户信息，false表示从服务器获取

    'socketURL': 'ws://192.168.75.1:8080/quanzhou/ws/qzmj', //socket连接地址，胡哥电脑
    // 'socketURL': 'localhost', //socket连接地址，启用本地服务器
    // 'socketURL': 'ws://192.168.0.110:8080/quanzhou/ws/qzmj', //socket连接地址，小兵的电脑
    // 'socketURL': 'ws://192.168.0.105:8080/quanzhou/ws/qzmj', //socket连接地址，黄链电脑
    // 'socketURL': 'ws://120.24.57.27:8080/quanzhou/ws/qzmj', //socket连接地址，服务器上

    shareConfig: null, //存储微信分享相关对象

    musicVolume: false, //音乐大小(背景音乐声音) //true 表示打开， false 表示关闭
    effectVolume: true, //音效大小(点击声音) //true 表示打开， false 表示关闭
    language: 'puTongHua', //是何种语言 mingNan  puTongHua
    extension: '.mp3', //默认扩展名

    hall: null, //大厅的对象, 不用重置
    setAlert: null, //设置弹出框
    offlineAlert: null, //断网时的弹出框
    roomGlobalRes: null, //room中的全局资源
    roomSceneFinished: false, //标记room场景已加载完成，当第四个人加入进来时，会同时收到 join和start消息，可能在场景还未加载完成时就运行start消息而报错
    joinOver: false, //标记是否四个用户均加入了
    buHuaOver: false, //标记是否四个用户均补花完成
    rebuildFinished: false };

//如果是生产环境
if (myGlobal.model === 'produce') {
    myGlobal.socketURL = 'ws://120.24.57.27:8080/quanzhou/ws/qzmj'; //socket连接地址，服务器上
    myGlobal.isBuildUserInfo = false; //是否自己构建用户信息，false表示从服务器获取，true表示从本地取
}

//自己构建用户信息
if (myGlobal.isBuildUserInfo) {
    window.userInfo = {
        openId: "10101",
        userId: 10101,
        nickName: "玩家10101",
        faceIcon: '5350b36d56b6e054.jpg',
        sex: 1, //1表示男 2表示女
        ip: '192.168.0.101',
        roomId: ''
    };
}

myGlobal.setGI = function (name, value) {
    //改变一个全局变量
    myGlobal[name] = value;
};

myGlobal.getGI = function (name) {
    //取得一个全局变量
    return myGlobal[name];
};

module.exports = myGlobal;

cc._RFpop();
},{}],"global":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'daabf/WfUpBYKp8YSXeDxnn', 'global');
// scripts\common\global.js

'use strict';

window.clientUser = null; //客户对象
window.room = null; //房间对象
window.socket = null; //socket对象, 不用重置
window.ajaxMng = null; //ajax对象, 不用重置
window.audio = null; //处理全部音频播放的对象，在game.js中创建, 不用重置
window.msgCache = null; //缓存消息对象

window.MYGLOBAL = null; //用于自定义的全局变量
window.helpTextArr = null; //帮助弹出框中的内容，会是一个一维数组
//window.userInfo,只有当window.MYGLOBAL.isBuildUserInfo === true时，才会自己创建，否则用后能传过来的数据
window.reset = null; //当八局结束或解散成功后回到大厅页面之前要做的重置工作
window.msgCondition = null; //缓存消息的条件配置对象
window.messageType = null; //消息的类型号对象
window.controllerMap = null; //消息的地图对象
window.words = null; //聊天的默认文字
window.TOOL = null; //工具函数

/*************************************** 以上为全部的全局变量 ********************************************************/

//缓存消息的条件配置对象
window.msgCondition = require('conditions'); //{start: [condition1, condition2], ready: [condition3]}

//帮助弹出框中的内容
window.helpText = require('helpText');

//自定义的全局变量
window.MYGLOBAL = require('globalData');
window.setGI = window.MYGLOBAL.setGI; //全局方法，更改 window.MYGLOBAL 对像的属性值
window.getGI = window.MYGLOBAL.getGI; //全局方法，取得 window.MYGLOBAL 对像的属性值

//消息配置
var msgConfig = require('msgConfig');
window.messageType = msgConfig.messageType; //消息的类型号
window.controllerMap = msgConfig.controllerMap; //消息的地图

//聊天的默认文字
window.words = require('dialogWords');

//工具函数
window.TOOL = require('tools');

//页面调试对象
window.pageLog = require('pageLog');

window.CONDITIONS = require('conditions');

//测试数据
window.testData = require('test_data');

/**
 * 八局结束或解散房间后重置全局资源
 */
window.reset = function () {
    TOOL.resetData();
    window.audio && window.audio.stopBgMusic();
    // clientUser && clientUser.resetJu();
    window.msgCache && window.msgCache.reset();
    //更改分享link，去掉房间号
    TOOL.replaceShareUrl('wx');
    window.room = null; //房间
    // window.clientUser.resetJu(); //客户重置(注意，不能置成null，因为它是在登录的时候创建的)
    window.socket.reset(); //重置所有的控制器

    window.MYGLOBAL.roomGlobalRes = null; //room中的全局资源
    window.MYGLOBAL.roomSceneFinished = false; //标记room场景已加载完成，当第四个人加入进来时，会同时收到 join和start消息，可能在场景还未加载完成时就运行start消息而报错
};

cc._RFpop();
},{"conditions":"conditions","dialogWords":"dialogWords","globalData":"globalData","helpText":"helpText","msgConfig":"msgConfig","pageLog":"pageLog","test_data":"test_data","tools":"tools"}],"handCardMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '958fbeKsDpDObzMYia3wqW+', 'handCardMng');
// scripts\room\view\userLayer\display\handCardMng.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * @class
 * @classdesc -- 这个类直接用于自己的手中的牌的显示(而不是其它人)
 */
var CardHandMng = cc.Class({
    name: 'CardHandMng',

    /**
     * @constructor
     * @param {Object} userSelf -- 用户对象
     */
    ctor: function ctor() {
        this.user = arguments[0]; //用户节点
        this.userNode = arguments[0].attr('userNode'); //用户节点
        this.mj_Atlas = getGI('roomGlobalRes').mj_Atlas; //麻将牌的图集
        this.n = this.userNode.name.substr(-1, 1); //取最后一个字符

        this.cardsCoord = []; //记录每张牌的原始坐标
        this.currentModel = 'handd'; //当前显示模式： handd 或 hand

        this.handPosition = this.userNode.getChildByName('hand').getPosition();
        this.handdPosition = this.userNode.getChildByName('handd').getPosition();

        this.convertHM('handd');
        this.youJingCards = null; //记录可以游的牌

        // //记录每张牌的原始坐标
        // for(var i=0; i<this.cardNodes.length; i++){
        //     this.cardsCoord.push( this.cardNodes[i].getPosition() );
        // }

        this.cardsHandOrder = arguments[0].attr('cardsHandOrder'); //时该记录每个节点对应于哪一张牌，以后出牌时，选中节点后才知道是哪张牌
        this.mjBPointer = -1; //当手牌是正面或背面时，指示当前已经显示到什么位置了
    },

    convertHM: function convertHM(hand) {

        if (this.cardNodes) {
            //恢复每张牌的原始坐标
            for (var i = 0; i < this.cardNodes.length; i++) {
                this.cardNodes[i].setPosition(this.cardsCoord[i]);
            }
        }

        this.handNode = this.userNode.getChildByName(hand);
        this.currentModel = hand; //更改当前显示模式

        var handActive = hand === 'hand' ? true : false;
        this.userNode.getChildByName('hand').active = handActive;
        this.userNode.getChildByName('handd').active = !handActive;

        this.cardNodes = this.handNode.children; //手中牌对应的节点， 是一个数组，14个元素

        this.cardNodes.sort(function (a, b) {
            //按名字大小排序，升序
            return a.name.match(/mj(.*)$/)[1] - b.name.match(/mj(.*)$/)[1];
        });

        //记录每张牌的原始坐标
        for (var i = 0; i < this.cardNodes.length; i++) {
            this.cardsCoord[i] = this.cardNodes[i].getPosition();
        }
    },

    /**当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        console.log('cardHandMng.js重置了。');
        //恢复每张牌的原始坐标
        for (var i = 0; i < this.cardNodes.length; i++) {
            this.cardNodes[i].setPosition(this.cardsCoord[i]);
        }
        //隐藏所有的牌
        this.hideHand();
        this.youJingCards = null; //记录可以游的牌
    },

    displayHand: function displayHand(cdAOrCdN, whichMj) {
        this.youJingCards = this.user.attr('youJingCards');
        if (this.n == 3) {
            this.displayHandL(cdAOrCdN, whichMj);
        } else {
            this.displayHandR(cdAOrCdN, whichMj);
        }
        this.youJingCards = null;
    },

    /**
     * 显示手中的牌, 最多13张牌
     * @public
     * @param {Array|number}  cdAOrCdN -- 当传入一维数组 ['w2', 'o1', 'i2'] 时， 可以将牌显示出来
     *                                  当传入 牌的张数 时， 就认为不是自己，所以只能看到牌的背面
     * @param {number} type -- 刷新牌的原因，如果是拿牌或庄家第一次的牌，则要占用 出牌的位置 此时 type取 'inCard' 'wantOutCard'
     */
    displayHandR: function displayHandR(cdAOrCdN, whichMj) {
        //将手中的麻将的数据刷新到页面, 最多13张牌
        if ((typeof whichMj === 'undefined' ? 'undefined' : _typeof(whichMj)) === 'object') {
            this.convertHM(whichMj); // 'hand' 'handd'
        }

        var cardsNum; //牌的总张数
        if (typeof cdAOrCdN === 'number') {
            cardsNum = cdAOrCdN;
        } else {
            cardsNum = cdAOrCdN.length;
        }

        var handIndex = cardsNum - 1,
            cardName = 'mjB'; //默认显示背面

        if (cardsNum === 17 || whichMj === 'wantOutCard') {
            var start = this.cardNodes.length - 1;
        } else {
            var start = this.cardNodes.length - 2;
            this.cardNodes[start + 1].active = false; //隐藏最后一张牌
        }
        //注意，这里是倒序遍历
        for (var i = start; i > -1; i--) {
            if (handIndex > -1) {
                //只将手中的牌显示出来

                if ((typeof cdAOrCdN === 'undefined' ? 'undefined' : _typeof(cdAOrCdN)) === 'object') {
                    //如果有牌
                    cardName = cdAOrCdN[handIndex]; //要显示的牌的名字 如：'w3'
                    this._recordCardName_(this.cardNodes[i].name, cardName); //记录显示了的牌，以后点击时才知道选择的节点对应哪一张牌
                }
                //更改麻将牌
                this._setMj_(this.cardNodes[i], cardName);
                this.cardNodes[i].active = true; //显示
                handIndex--;
            } else {
                //多余的隐藏
                this.cardNodes[i].active = false; //隐藏
            }
        }

        this._handNodeMove_(cardsNum);
    },

    /**
     * 移动两侧的手牌的位置
     * @private
     * @param {number} num -- 要显示的牌的张数
     */
    _handNodeMove_: function _handNodeMove_(num) {
        var n = this.n;
        var totalH,
            a,
            isChange = false,
            x,
            y;

        if (n == 1) {
            totalH = 340;
            a = 78.7 * 3.14 / 180;
            isChange = true;
            x = -1;
            y = 1;
        } else if (n == 3) {
            totalH = 340;
            a = 78.7 * 3.14 / 180;
            isChange = true;
            x = 1;
            y = 1;
        }

        if (isChange) {
            y = y * totalH / 17 * (17 - num) / 2;
            x = 0; //x* y / Math.tan(a);
            var originalP = this.currentModel === 'handd' ? this.handdPosition : this.handPosition;

            var newP = originalP.add(cc.v2(x, y));
            this.handNode.setPosition(newP);
        }
    },

    /**
     * 显示手中的牌, 最多13张牌
     * @public
     * @param {Array|number}  cdAOrCdN -- 当传入一维数组 ['w2', 'o1', 'i2'] 时， 可以将牌显示出来
     *                                  当传入 牌的张数 时， 就认为不是自己，所以只能看到牌的背面
     * @param {number} type -- 刷新牌的原因，如果是拿牌或庄家第一次的牌，则要占用 出牌的位置 此时 type取 'inCard' 'wantOutCard'
     */
    displayHandL: function displayHandL(cdAOrCdN, whichMj) {
        //将手中的麻将的数据刷新到页面, 最多13张牌
        if ((typeof whichMj === 'undefined' ? 'undefined' : _typeof(whichMj)) === 'object') {
            this.convertHM(whichMj); // 'hand' 'handd'
        }

        //恢复拿牌时移动过的那张牌
        if (this.n == 3 && this.mjBPointer !== -1) {
            this.mjBPointer = this.mjBPointer > 16 ? 16 : this.mjBPointer;
            this.cardNodes[this.mjBPointer].setPosition(this.cardsCoord[this.mjBPointer]);
        }

        var cardsNum; //牌的总张数
        if (typeof cdAOrCdN === 'number') {
            cardsNum = cdAOrCdN;
        } else {
            cardsNum = cdAOrCdN.length;
        }

        var start = 0;
        var handIndex = 0,
            cardName = 'mjB';

        //注意，这里是正序遍历
        for (var i = start; i < this.cardNodes.length; i++) {
            if (cardsNum > handIndex) {
                //只将手中的牌显示出来

                if ((typeof cdAOrCdN === 'undefined' ? 'undefined' : _typeof(cdAOrCdN)) === 'object') {
                    //如果有牌
                    cardName = cdAOrCdN[handIndex]; //要显示的牌的名字 如：'w3'
                    this._recordCardName_(this.cardNodes[i].name, cardName); //记录显示了的牌，以后点击时才知道选择的节点对应哪一张牌
                }
                //更改麻将牌
                this._setMj_(this.cardNodes[i], cardName);
                this.cardNodes[i].active = true; //显示
                handIndex++;
            } else {
                //多余的隐藏
                this.cardNodes[i].active = false; //隐藏
            }
        }

        this.mjBPointer = cardsNum; //记录牌的张数
        if (this.n == 3 && cardsNum === 17) {
            this._move3Incard_(this.cardNodes[16], 17); //向左上方移动一定距离
        }

        this._handNodeMove_(cardsNum);
    },

    /**
     * 右面的人拿到一张牌后，拿到的那张牌向左上方移动一定距离
     * @private
     */
    _move3Incard_: function _move3Incard_(node, cardsNum) {
        node.setPosition(node.getPosition().add(cc.v2(-5, 15).mulSelf(-1 / 32 * (cardsNum - 1) + 1)));
    },

    /**
     * 拿一张牌，只显示到最右边(而不是全部刷新手中的牌)， 出了牌才全部刷新
     * @param {string} cardName -- 要显示的牌的名字 可以是 '' | 'w3'
     */
    inCard: function inCard(cardName) {

        var positionIndex;
        if (this.n == 3) {
            this.mjBPointer = this.mjBPointer > 16 ? 16 : this.mjBPointer;
            positionIndex = this.mjBPointer;
            this._move3Incard_(this.cardNodes[positionIndex], positionIndex); //向左上方移动一定距离
        } else {
            positionIndex = this.cardNodes.length - 1; //最后一张
        }

        //说明是客户
        if (cardName) {
            this.youJingCards = this.user.attr('youJingCards');
            this._recordCardName_(this.cardNodes[positionIndex].name, cardName); //记录显示了的牌，以后点击时才知道选择的节点对应哪一张牌
            if (this.youJingCards && this.youJingCards.length > 0) {
                var mjNode = void 0;
                for (var i = 0; i < 17; i++) {
                    mjNode = this.cardNodes[i];
                    if (mjNode.active) {
                        //如果这张牌是显示的
                        if (this.youJingCards.indexOf(this.cardsHandOrder[mjNode.name]) !== -1) {
                            mjNode.getChildByName('reminder').active = true;
                            mjNode.getChildByName('reminder').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame('youjin');
                        } else {
                            mjNode.getChildByName('reminder').active = false;
                        }
                    }
                }
            }
        } else {
            cardName = 'mjB';
        }
        //更改麻将牌
        this._setMj_(this.cardNodes[positionIndex], cardName);
        this.cardNodes[positionIndex].active = true; //显示
        this.youJingCards = null;
    },

    /**
    * 隐藏手中所有牌
    * @public
    */
    hideHand: function hideHand() {
        for (var i = 0; i < this.cardNodes.length; i++) {
            this.cardNodes[i].active = false; //隐藏
        }
    },

    /**
     * 隐藏听时的出现的遮罩层
     * @public
     */
    hideTingMask: function hideTingMask() {
        for (var i = 0; i < this.cardNodes.length; i++) {
            //遍历每一张牌
            this.cardNodes[i].getChildByName('mashMask').active = false;
        }
    },

    /**
     * 记录手中节点名：牌的名字, 操作 this.cardsHandOrder对象
     * @private
     */
    _recordCardName_: function _recordCardName_(nodeName, cardName) {
        this.cardsHandOrder[nodeName] = cardName;
    },

    /**
     * 通过手中节点名获取牌的名字, 操作 this.cardsHandOrder对象
     * @private
     */
    _getCardNameFromNode_: function _getCardNameFromNode_(nodeName) {
        return this.cardsHandOrder[nodeName];
    },

    /**
     * 更改麻将牌
     * @private
     * @param {cc.Node} mjNode -- 麻将节点
     * @param {string} cardName -- 牌的名字
     */
    _setMj_: function _setMj_(mjNode, cardName) {
        var spriteFrame = mjNode.getComponent(cc.Sprite).spriteFrame.name;
        var huaNode;
        switch (cardName) {
            case 'mjB': //显示为背面
            case 'mjF':
                //显示为正面
                this._mjReverSal_(mjNode, cardName);
                break;
            default:
                //显示为正面，并更改牌的花色
                huaNode = mjNode.getChildByName('hua');
                if (this.currentModel === 'handd') {
                    //当牌倒着时，才有前面
                    this._mjReverSal_(mjNode, 'mjF');
                } else {
                    if (!huaNode) {
                        return;
                    }
                }
                huaNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardName);
                if (cardName === room.goldCard) {
                    //如果是金牌
                    mjNode.getChildByName('jinLabel').active = true;
                } else {
                    mjNode.getChildByName('jinLabel').active = false;
                }

                if (this.youJingCards && this.youJingCards.length > 0) {
                    if (this.youJingCards.indexOf(cardName) !== -1) {
                        mjNode.getChildByName('reminder').active = true;
                        mjNode.getChildByName('reminder').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame('youjin');
                    } else {
                        mjNode.getChildByName('reminder').active = false;
                    }
                } else {
                    mjNode.getChildByName('reminder').active = false;
                }
        }
    },

    /**
     * 显示为正面或反面
     * @private
     * @param {cc.Node} mjNode -- 麻将节点
     * @param {string} face -- 显示为哪一面
     */
    _mjReverSal_: function _mjReverSal_(mjNode, face) {
        var spriteFrame = mjNode.getComponent(cc.Sprite).spriteFrame.name;
        switch (face) {
            case 'mjB':
                //显示为背面
                if (this.currentModel === 'hand') {
                    //当牌立着时，没有背面
                    return;
                }
                if (spriteFrame.charAt(0) === 'h') {
                    if (spriteFrame.search('handb') === -1) {
                        //如果是正面
                        spriteFrame = spriteFrame.replace('handm', 'handb');
                        mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                    }
                } else {
                    if (spriteFrame.search('mingb') === -1) {
                        //如果是正面
                        spriteFrame = spriteFrame.replace('ming', 'mingb');
                        mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                    }
                }

                mjNode.getChildByName('hua').active = false;
                break;
            case 'mjF':
                //显示为正面

                if (spriteFrame.charAt(0) === 'h') {
                    if (spriteFrame.search('handb') !== -1) {
                        //如果是背面
                        spriteFrame = spriteFrame.replace('handb', 'handm');
                        mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                    }
                } else {
                    if (spriteFrame.search('mingb') !== -1) {
                        //如果是背面
                        spriteFrame = spriteFrame.replace('mingb', 'ming');
                        mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                    }
                }

                mjNode.getChildByName('hua').active = true;

                break;
        }
    }
});

module.exports = CardHandMng;

cc._RFpop();
},{}],"handCardsBase":[function(require,module,exports){
"use strict";
cc._RFpush(module, '650ffS9R/VHa7lANQrUPQIn', 'handCardsBase');
// scripts\room\model\cardsMng\_cardsData_\handCardsBase.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**麻将编码
 * 红花: 'i1', 'i2', 'i3', 'i4' -- 春夏秋冬
 * 黑花: 'h1', 'h2', 'h3', 'h4' -- 梅兰竹菊
 * 风牌: 'f1', 'f2', 'f3', 'f4' -- 东南西北
 * 箭牌: 'j1', 'j2', 'j3' -- 中发白
 * 序数牌: o1~o9 -- 筒牌  t1~t9 -- 条牌  w1~w9 -- 万牌
 */

/**
 * @class
 * @classdesc -- 管理手中的牌：只包括对 手中的牌 的基本操作，不包括统计手中的牌, 不用作实体类
 */
var HandCardsBase = cc.Class({
    name: 'HandCardsBase',

    /**
     * @constructor
     */
    ctor: function ctor() {
        var config = arguments[0];
        this._init();
    },

    /**
     * 本类初始化
     * @protected
     */
    _init: function _init() {

        //存储索引, 指定生成的二维数组中每一个数的表示的含义
        //'i'-- 红花  'h' -- 黑花 'f'--风牌类型  'j'--箭牌类型  'o'--筒  't'--条  'w'--万
        this.order = ['w', 't', 'o', 'f', 'j', 'i', 'h'];
        this._copyOrderProp_(); //复制this.order中的属性 'i': 0 --> '0': 'i', 并将this.order转换成一个对象

        //玩家最开始时拿到的牌，一直不会变, 形如 ['i3', 'w3', 'o2'] 或 是一个数字
        this.originalCardsHand = [];

        //手中的牌
        this.cardsHand = [//w(万)                     // t(条)                        // o(筒)
        [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0],
        //f1,f2,f3,f4(东南西北) //j1,j2,j3(中发白)  //i1,i2,i3,i4(春夏秋冬)  //h1,h2,h3,h4(梅兰竹菊)
        [0, 0, 0, 0], [0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]];
        this.goldCards = []; //记录拿到的金牌
        this.goldCard = ''; //记录本局的金牌是哪一张
        this.cardsHandArr = []; //手中牌的一维数组，开始打牌后，会改变，始终与 this.cardsHand同步
        this.cardsNum = 0; // 手中牌的张数，每出一张牌，拿一张牌，都会变化
    },

    /**
     * 复制this.order中的属性 'i': 0 --> '0': 'i'
     * @private
     */
    _copyOrderProp_: function _copyOrderProp_() {
        var orderCopy = {};
        for (var i = 0; i < this.order.length; i++) {
            orderCopy[i] = this.order[i];
            orderCopy[this.order[i]] = i;
        }
        this.order = orderCopy;
    },

    /**
     * 添加一副牌
     * @public
     * @param {Object|Number} originalCardsHand -- 传入一个一维数组 或 一个数字
    */
    setCards: function setCards(originalCardsHand) {
        this.goldCard = room.goldCard; //设置金牌
        if ((typeof originalCardsHand === 'undefined' ? 'undefined' : _typeof(originalCardsHand)) !== 'object') {
            //就说明手中没有牌
            this.cardsNum = originalCardsHand; // 手中牌的张数，每出一张牌，拿一张牌，都会变化
            this.originalCardsHand = this.cardsNum;
        } else {
            this.originalCardsHand.length = 0;
            TOOL.copyArray(this.originalCardsHand, originalCardsHand); //将数据复制进来
            this._clearCardsHand_(); //将手中的牌清0
            this._convertToCardsHand_(); //将拿到的原始的牌转换成一个二维数组
            this.cardsNum = originalCardsHand.length; // 手中牌的张数，每出一张牌，拿一张牌，都会变化
        }
    },

    /**
     * 每一局开始时重置对象中的参数
     * @public
     * @param {Object} config -- {
     *                              originalCardsHand: [ 'e', 'w2', 't8', 'n'] //不可省略， 玩家最开始时拿到的牌
     *                            }
    */
    reset: function reset() {
        console.log('handCardsBase.js重置了。');
        if (_typeof(this.originalCardsHand) === 'object') {
            //说明手中有牌
            this.originalCardsHand.length = 0;
            this._clearCardsHand_(); //将手中的牌清0
            this.cardsHandArr.length = 0; //手中牌的一维数组，开始打牌后，会改变
        }
        this.cardsNum = 0; // 手中牌的张数，每出一张牌，拿一张牌，都会变化
        this.goldCards.length = 0; //记录拿到的金牌
        this.goldCard = ''; //记录本局的金牌是哪一张
    },

    /**
     * 更改手中某一张牌的数量
     * @public
     * @param {string} cardName --要更改的牌的名字
     * @param {number} num -- 要操作的数量
     * @param {string} operate --要执行的操作的名字，只能取：'add' //在原来数量的基础上增加num张牌  'set' //将牌的数量直接改成num  'sub' //在原来数量的基础上减掉 num张牌
     * @returns {number} -- 更改完成后，手中这张牌的数量
     */
    operateCard: function operateCard(cardName, num, operate) {
        if (_typeof(this.originalCardsHand) !== 'object') {
            //说明手中没有牌
            switch (operate) {
                case 'add':
                    this.cardsNum += num;
                    break;
                case 'set':
                    this.cardsNum = num;
                    break;
                case 'sub':
                    this.cardsNum -= num;
                    break;
            }
            return this.cardsNum;
        }

        if (cardName === this.goldCard) {
            //如果是金牌
            switch (operate) {
                case 'add':
                    this.goldCards.push(cardName);
                    break;
                case 'set':
                    this.goldCards.length = num;
                    break;
                case 'sub':
                    this.goldCards.pop();
                    break;
            }
            return this.goldCards.length;
        }

        var coord = this._getCardCoord(cardName);
        switch (operate) {
            case 'add':
                this.cardsHand[coord.i][coord.j] += num;
                this.cardsNum += num;
                break;
            case 'set':
                this.cardsHand[coord.i][coord.j] = num;
                this.cardsNum += num - this.cardsHand[coord.i][coord.j];
                break;
            case 'sub':
                this.cardsHand[coord.i][coord.j] -= num;
                this.cardsNum -= num;
                break;
        }
        return this.cardsHand[coord.i][coord.j];
    },

    /**
     * 用于升序遍历手中的全部的牌
     * @public
     * @param {function} callback -- 必须 callback.call(thisObj, callbackArg);
     *                                   其中 callbackArg = {typeName: 't', cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
     *                              callback: 返回 'breakOuter' 表示退出整个循环， 'breakInner'表示退出内层循环
     *                                             'continumOuter' 表示continue整个循环， 'continueInner'表示continue内层循环
     * @param {this} thisObj --可省略 回调函数中this的指向，如果不传入本对像，就默认取 handCards对像
    */
    forEach: function forEach(callback, thisObj) {
        //用于遍历全部的牌
        thisObj = thisObj || this;
        var arr = null,
            //循环时记录内层数组
        len = this.cardsHand.length,
            //外层数组的长度
        result = null,
            //回调函数的返回值
        callbackArg = //传入回调函数的参数
        { num: 0, cardName: '', typeName: '', i: 0, j: 0, arr: null, cardsHand: null };

        outer: for (var i = 0; i < len; i++) {
            arr = this.cardsHand[i];
            inner: for (var j = 0; j < arr.length; j++) {

                callbackArg.num = arr[j]; //牌的数量
                callbackArg.cardName = this._getCardNameFromCoord(i, j); //牌的名字
                callbackArg.typeName = this.order[i]; //牌的类名，如: 'f'(风类牌)，'j'(箭类牌)，'o'(筒类牌)，'t'(条类牌)，'w'(万类牌)
                callbackArg.j = j; //牌的j坐标
                callbackArg.i = i; //牌的i坐标
                callbackArg.arr = arr; //某种牌的(风、箭、万、条、筒)的一维数组
                callbackArg.cardsHand = this.cardsHand; //手中的牌的二维数组

                result = callback.call(thisObj, callbackArg);
                switch (result) {
                    case 'breakOuter':
                        //如果返回 breakOuter，就退出整个循环
                        break outer;
                    case 'breakInner':
                        //如果返回 breakInner，就退出内层循环
                        break inner;
                    case 'continueInner':
                        //如果返回 continueInner，就continue内层循环
                        continue inner;
                    case 'continumOuter':
                        //如果返回 continumOuter，就continue整个循环
                        continue outer;
                }
            }
        }
    },

    /**
     * 用于倒序遍历手中的全部的牌
     * @public
     * @param {function} callback -- 必须 callback.call(thisObj, callbackArg);
     *                                   其中 callbackArg = {typeName: 't', cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
     *                              callback: 返回 'breakOuter' 表示退出整个循环， 'breakInner'表示退出内层循环
     *                                             'continumOuter' 表示continue整个循环， 'continueInner'表示continue内层循环
     * @param {this} thisObj -- 可省略 回调函数中this的指向，如果不传入本对像，就默认取 handCards对像
    */
    forEachReduce: function forEachReduce(callback, thisObj) {
        thisObj = thisObj || this;
        var arr = null,
            //循环时记录内层数组
        len = this.cardsHand.length,
            //外层数组的长度
        result = null,
            //回调函数的返回值
        callbackArg = //传入回调函数的参数
        { num: 0, cardName: '', typeName: '', i: 0, j: 0, arr: null, cardsHand: null };

        outer: for (var i = len - 1; i > -1; i--) {
            arr = this.cardsHand[i];
            inner: for (var j = arr.length - 1; j > -1; j--) {

                callbackArg.num = arr[j]; //牌的数量
                callbackArg.typeName = this.order[i]; //牌的类名，如: 'f'(风类牌)，'j'(箭类牌)，'o'(筒类牌)，'t'(条类牌)，'w'(万类牌)
                callbackArg.cardName = this._getCardNameFromCoord(i, j); //牌的名字
                callbackArg.j = j; //牌的j坐标
                callbackArg.i = i; //牌的i坐标
                callbackArg.arr = arr; //某种牌的(风、箭、万、条、筒)的一维数组
                callbackArg.cardsHand = this.cardsHand; //手中的牌的二维数组

                result = callback.call(thisObj, callbackArg);
                switch (result) {
                    case 'breakOuter':
                        //如果返回 breakOuter，就退出整个循环
                        break outer;
                    case 'breakInner':
                        //如果返回 breakInner，就退出内层循环
                        break inner;
                    case 'continueInner':
                        //如果返回 continueInner，就continue内层循环
                        continue inner;
                    case 'continumOuter':
                        //如果返回 continumOuter，就continue整个循环
                        continue outer;
                }
            }
        }
    },

    /**
     * 只是外层循环的遍历手中的牌
     * @public
     * @param {function} callback -- 必须 callback.call(thisObj, callbackArg);
     *                                   其中 callbackArg = { typeName: 't',j: 1, arr:[...], cardsHand: [...] }
     *                              callback: 返回 'break' 表示退出整个循环， 'continue'表示continue循环
     * @param {this} thisObj -- 可省略 回调函数中this的指向，如果不传入本对像，就默认取 handCards对像
     */
    outerForEach: function outerForEach(callback, thisObj) {
        thisObj = thisObj || this;

        var len = this.cardsHand.length,
            //外层数组的长度
        result = null,
            //回调函数的返回值
        callbackArg = //传入回调函数的参数
        { typeName: '', i: 0, arr: null, cardsHand: null };

        outer: for (var i = 0; i < len; i++) {

            callbackArg.typeName = this.order[i]; //牌的类名，如: 'f'(风类牌)，'j'(箭类牌)，'o'(筒类牌)，'t'(条类牌)，'w'(万类牌)
            callbackArg.i = i; //牌的i坐标
            callbackArg.arr = this.cardsHand[i]; //某种牌的(风、箭、万、条、筒)的一维数组
            callbackArg.cardsHand = this.cardsHand; //手中的牌的二维数组

            result = callback.call(thisObj, callbackArg);
            switch (result) {
                case 'break':
                    //如果返回 break，就退出循环
                    break outer;
                case 'continue':
                    //如果返回 continue，就continue循环
                    continue outer;
            }
        }
    },

    /**
     * 获取手中牌的总数量
     * @returns {number} -- 手中牌的数量
     */
    getLen: function getLen() {
        if (_typeof(this.originalCardsHand) !== 'object') {
            //说明手中没有牌
            return this.cardsNum;
        }

        var num = 0;
        //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
        this.forEach(function (data) {
            num += data.num;
        });

        return num + this.goldCards.length;
    },

    /**
     * 查询手中某张牌的数量
     * @public
     * @param {string} cardName -- 必须 要查询的牌的名字 如：'w3'
     * @returns {number} -- 查询的结果(传入的牌的对应的数量)
    */
    getCardNums: function getCardNums(cardName) {
        var cardCoord = this._getCardCoord(cardName); //牌的坐标
        return this.cardsHand[cardCoord.i][cardCoord.j]; //牌的数量
    },

    /**
     * 根据坐标返回对应牌的名字， 'e', 't4'
     * 示例： i=0, j=2 ==》 'x' ;  i=2, j=4 ==》 'o5' ;
     * @protected
     * @param {number|Object} i -- 这张牌位于从左往右数的第几个一维数组中， 从0开始计数，
     *                             取值范围 [0,4]，分别对应：'0': 'f'(风牌), '1': 'j'(箭牌), '2': 'o'(筒牌), '3': 't'(条牌), '4': 'w'(万牌)
     *      如果第一个参数是一个对象，则表示，只传入{i: 1, j: 3}这种情况
     * @param {number} j -- （当第一个参数传入一个对象时，本参数不起作用。） 这张牌位于第i个一维数组中的位置 对于风牌取值:[0,3], 对于箭牌取值: [0,2], 对于筒、条、万牌取值: [0,8]
     * @returns {string} -- 传入的坐标对应的牌的名字 如: 'z', 'f', 'b', 't1', 'o3', 'w3', 'e', 'x', 'n', 's'
     */
    _getCardNameFromCoord: function _getCardNameFromCoord(i, j) {
        if ((typeof i === 'undefined' ? 'undefined' : _typeof(i)) === 'object') {
            //如果第一个参数是一个对象，则不再看第二个参数
            i = i.i;
            j = i.j;
        }
        return this.order[i] + (j + 1);
    },

    /**
     * 获取指定牌的坐标, 与 _getCardNameFromCoord 的作用相反
     * 示例：'o5' ==》  {i: 2, j: 4};
     * @protected
     * @param {string}  cardName -- 牌的名字 如： 'x', 't8'
     * @returns {Object} -- {i: 3, 'j': 7},
     *                      其中i表示: 这张牌位于从左往右数的第几个一维数组中， 从0开始计数，
     *                                 取值范围 [0,6]，分别对应：'0': 'f'(风牌), '1': 'j'(箭牌), '2': 'o'(筒牌), '3': 't'(条牌), '4': 'w'(万牌)
     *                      其中j表示: 这张牌位于第i个一维数组中的位置 对于风牌取值:[0,3], 对于箭牌取值: [0,2], 对于筒、条、万牌取值: [0,8]
     */
    _getCardCoord: function _getCardCoord(cardName) {
        return { i: this.order[cardName.charAt(0)], j: cardName.charAt(1) - 1 };
    },

    /**
     * 将拿到的原始的牌(一维数组)转换成一个二维数组 （this.originalCardsHand --》 this.cardsHand）
     * 示例：对于玩家拿到的牌 this.originalCardsHand = [ 'e', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2', 't8', 'w2', 't8', 'w2', 'n']
     * 转换的结果 this.cardsHand = [ [0, 0, 0, 0], [0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0] ]
     * @private
    */
    _convertToCardsHand_: function _convertToCardsHand_() {
        var len = this.originalCardsHand.length;
        console.log('转换时：' + this.originalCardsHand);
        for (var i = 0; i < len; i++) {
            var cardName = this.originalCardsHand[i];
            if (this.goldCard === cardName) {
                this.goldCards.push(cardName); //金牌
            } else {
                this.cardsHand[this.order[cardName.charAt(0)]][cardName.charAt(1) - 1]++;
            }
        }
    },

    /**
     * 将手中的牌全部清零 （即二维数组 this.cardsHand 中的每个元素置0 ）
     * 操作结果：this.cardsHand = [ [0, 0, 0, 0], [0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0] ]
     * @private
     * @returns {undefined}
    */
    _clearCardsHand_: function _clearCardsHand_() {
        //将手中的牌全部清零

        //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
        this.forEach(function (data) {
            data.arr[data.j] = 0;
        });
        this.goldCards.length = 0; // 金牌清空
    }

});

module.exports = HandCardsBase;

cc._RFpop();
},{}],"handCardsChild":[function(require,module,exports){
"use strict";
cc._RFpush(module, '09334+j+LZFbrurkDQzdULV', 'handCardsChild');
// scripts\room\model\cardsMng\cardsOperate\handCardsChild.js

'use strict';

var HandCards = require('handCards');

/**
 * @class
 * @classdesc -- 主要管理手中的牌的操作
 */
var HandCardsChild = cc.Class({
    name: 'HandCardsChild',
    extends: HandCards,
    /**
     * @constructor
     */
    ctor: function ctor() {
        this.cardsTypeNum = [0, 0, 0, 0, 0]; //对应0张的个数， 1张的个数， 对子的个数，刻子的个数， 杠子的个数
    },

    /**
    * 遍历手中的多类牌,如：遍历 风牌 + 箭牌
    * 调用方式示例： multiCardTypeForEach('f', 'j', function(data){});
    * @public
    * @param {string} cardType -- 必须，可以传入多个牌的类型，只能取 'f'(风牌)，'j'(箭牌)，'o'(筒牌)，'t'(条牌)，'w'(万牌)
    * @param {function} callback -- 必须,位于倒数第二个 callback.call(thisObj, callbackArg);
    *                                   其中 callbackArg = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
    *                                callback: 返回 'continue' 表示跳过一个值， 返回 'break' 表示跳过一个数组，返回 'return' 表示结束整个循环
    * @param {this} thisObj -- 可省略,位于倒数第一个 回调函数中this的指向，如果不传入本对像，就默认取 handCards对像
    */
    multiCardTypeForEach: function multiCardTypeForEach(cardType /*可以有多个cardType参数*/ /*, callback, thisObj 最后两个*/) {

        for (var k = 0; k < arguments.length; k++) {
            if (typeof arguments[k] !== 'string') {
                break;
            }
        }

        var result;
        //注意：k 指向 callback 的位置
        for (var m = 0; m < k; m++) {
            result = this.cardTypeForEach(arguments[m], arguments[k], arguments[k + 1]);
            switch (result) {
                case 'return':
                    //如果返回 return，就退出循环
                    return;
            }
        }
    },

    /**
     * 返回手中牌的字符串的表示
     * @public
     * @returns {string} -- 返回值形如： 'w3, w5, o8, x, e'
     */
    getAllCardsStr: function getAllCardsStr() {
        return this.cardsHandConvertToArr().toString();
    }

});

module.exports = HandCardsChild;

cc._RFpop();
},{"handCards":"handCards"}],"handCards":[function(require,module,exports){
"use strict";
cc._RFpush(module, '020d5e+jR1NpYMyvqx/5/80', 'handCards');
// scripts\room\model\cardsMng\_cardsData_\handCards.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var HandCardsBase = require('handCardsBase');

/**
 * @class
 * @classdesc -- 管理手中的牌：只包括对 手中的牌 的基本操作，不包括统计手中的牌
 */
var HandCards = cc.Class({
    name: 'HandCards',
    extends: HandCardsBase,
    /**
     * @constructor
     */
    ctor: function ctor() {
        /* false表示 中发白三个不一样的不能组成一个顺子，只能用来碰，刻子，各种扛。
        * true表示 中发白三个不一样的能组成一个顺子，只能用来碰，刻子，各种扛
        * 这里主要用来确定是不是能吃中发白
        */
        this.isZFBOrder = room.attr('isZFBOrder');
    },

    /**
     * 遍历手中的某一类牌,如：遍历风牌
     * 调用方式示例： cardTypeForEach('t', function(data){});
     * @public
     * @param {string} cardType -- 必须，只能传入一种牌的类型，只能取 'f'(风牌)，'j'(箭牌)，'o'(筒牌)，'t'(条牌)，'w'(万牌)
     * @param {function} callback -- 必须 callback.call(thisObj, callbackArg);
     *                                   其中 callbackArg = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
     *                               callback: 返回 'continue' 表示跳过一个值， 返回 'break' 或 'return'表示结束整个循环
     * @param {this} thisObj -- 可省略 回调函数中this的指向，如果不传入本对像，就默认取 handCards对像
     * @returns {number} -- 返回 -1 或 1 或 -2, -2这个值只用于 multiCardTypeForEach 函数中
     */
    cardTypeForEach: function cardTypeForEach(cardType, callback, thisObj) {
        thisObj = thisObj || this;

        var i = this.order[cardType];
        var arr = this.cardsHand[i]; //风牌的数组
        var len = arr.length,
            result = null;
        var callbackArg = {};

        outer: for (var j = 0; j < len; j++) {

            callbackArg.num = arr[j]; //牌的数量
            callbackArg.cardName = this._getCardNameFromCoord(i, j); //牌的名字
            callbackArg.typeName = this.order[i]; //牌的类名，如: 'f'(风类牌)，'j'(箭类牌)，'o'(筒类牌)，'t'(条类牌)，'w'(万类牌)
            callbackArg.j = j; //牌的j坐标
            callbackArg.i = i; //牌的i坐标
            callbackArg.arr = arr; //某种牌的(风、箭、万、条、筒)的一维数组
            callbackArg.cardsHand = this.cardsHand; //手中的牌的二维数组

            result = callback.call(thisObj, callbackArg);
            switch (result) {
                case 'break':
                    //如果返回 break，就退出循环
                    break outer;
                case 'return':
                    //如果返回 return，就退出循环
                    break outer;
                case 'continue':
                    //如果返回 continue，就continue循环
                    continue outer;
            }
        }

        return result;
    },

    /**
     * 检查是否有这张牌，如果没有，就返回手中的的某张牌
     * @public
     * @param {string} cardName -- 牌的名字
     */
    checkNum: function checkNum(cardName) {
        var num = this.getCardNums(cardName); //手中牌的数量
        if (num < 1) {
            //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
            this.forEach(function (data) {
                if (data.num > 0) {
                    cardName = data.cardName;
                    return 'breakOuter';
                }
            });
            return cardName;
        }
        return cardName;
    },

    /**
     * 手中的牌是不是有4个的
     * @private
     * @returns {Array} -- 返回[]说明没有4个的，否则返回['w4', 't2']
     */
    _getFourCard_: function _getFourCard_() {
        var fourCard = [];

        //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
        this.forEach(function (data) {
            if (data.num === 4) {
                fourCard.push(data.cardName);
            }
        });

        return fourCard;
    },

    /**
     * 获取手中的牌
     * @public
     * @returns {number|Array} -- 如果手中有牌，就返回一个牌的一维数组
     *                            如果手中没用牌，就返回一个数字，表示手中牌的数量
     */
    getHandCard: function getHandCard() {
        var _this = this;

        if (_typeof(this.originalCardsHand) !== 'object') {
            //如果手中没有牌
            return this.cardsNum; //返回牌的总数量
        } else {
            this.cardsHandArr.length = 0;
            this.goldCards.forEach(function (cardName) {
                _this.cardsHandArr.push(cardName);
            });
            //data = {typeName: 't',cardName:'w2', num: 2, j: 1, i: 4, arr:[...], cardsHand: [...] }
            this.forEach(function (data) {
                for (var k = 0; k < data.num; k++) {
                    this.cardsHandArr.push(data.cardName);
                }
            });
            return this.cardsHandArr; //返回一个牌的一维数组
        }
    }
});

module.exports = HandCards;

cc._RFpop();
},{"handCardsBase":"handCardsBase"}],"hand_operatePanel":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd835f4GsBdB8b07D+7U6zaI', 'hand_operatePanel');
// scripts\room\view\userLayer\display\hand_operatePanel.js

'use strict';

/**@class
 * @classdesc 控制选项面板的显示与隐藏
 */
var OperateEvent = cc.Class({
    name: 'OperateEvent',

    /**
     * @constructor
     * @param {Object} userNode
     */
    ctor: function ctor() {
        this.userNode = arguments[0];

        this.btnsNode = cc.find('operate/btns', this.userNode); //选择按钮的父节点
        this.btnsArr = this.btnsNode.getChildren();

        // this.btnsArr.sort(function(a,b){ //按名字大小排序，升序
        //     return a.name.slice(-1) - b.name.slice(-1);
        // });

        this.chooseNode = cc.find('operate/choose', this.userNode); //选择牌的父节点
        this.chooseArr = this.chooseNode.getChildren();
        //    this.chooseArr.sort(function(a,b){ //按名字大小排序，升序
        //         return a.name.slice(-1) - b.name.slice(-1);
        //     });
        this.chooseNode.active = false;
        this.btnsNode.active = false;
        this.isCanShowPanel = true; //是否可以弹出选项面板

        this.mj_Atlas = getGI('roomGlobalRes').mj_Atlas; //麻将牌的图集
        this.operate_Atlas = getGI('roomGlobalRes').operate_Atlas; //操作的图集

        this.gangArr = []; //杠的计算的位置
        this.chiArr = []; //吃的计算的位置
        this.showArr = ['guo']; //要显示的按钮的名字的数组
        this.currentArr = null; //它指向  gangArr 或 chiArr

        this.mjW = this.chooseArr[0].children[0].width;
    },

    /**当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        console.log('hand_operatePanel.js重置了。');
        this.chooseNode.active = false;
        this.btnsNode.active = false;
        this.isShowing = false; //标记选择面板是否还显示着 (如果起手就能听，而用户一直不选，到轮到自己摸到一张牌后，再选择听，就出不了牌了)
        this.isCanShowPanel = true; //是否可以弹出选项面板

        this.operateArr = null; //记录传入的操作集合，当点击选择时，才知道选择的相关数据
        this.gangArr.length = 0;
        this.chiArr.length = 0;
        this.showArr.length = 1;
        this.currentArr = null; //它指向  gangArr 或 chiArr
    },

    /**弹出 选项面板
     * @public
     * @param {Array} operateArr ---
     * operateArr =  [
        {p: '289383',  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
        {p: '289383',  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] }, //明杠
        {p: '289383',  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] }, //暗杠
        {p: '289383',  t: 'buGang', c: 'w3', from: 3892938, m:['w3'] }, //补杠, 与碰是分开的
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w3', 'w4', 'w2'] }, //吃
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3', 'w2'] }, //吃
          {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
          {p: 289383,  t: 'sanJingDao', c: '', from: 3892938, m:null }, //三金倒
        {p: 289383,  t: 'youJing', c: '', from: 3892938, m:['w2', 't4'] }, //游金
        {p: 289383,  t: 'shuangYou', c: '', from: 3892938, m:['w2', 't4'] }, //双游
        {p: 289383,  t: 'sanYou', c: '', from: 3892938, m:['w2', 't4'] }, //三游
          {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
        {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
     *  ]
    */
    showPanel: function showPanel(operateArr) {
        if (!this.isCanShowPanel) {
            //如果不可以弹出，就直接退出
            return;
        }
        this.operateArr = operateArr; //记录传入的操作集合，当点击选择时，才知道选择的相关数据

        this.gangArr.length = 0; //清空
        this.chiArr.length = 0; //清空
        this.showArr.length = 1; //清空， 只留第一个 '过'  = ['guo'];

        var gangArr = this.gangArr;
        var chiArr = this.chiArr;
        var showArr = this.showArr;

        for (var i = 0; i < operateArr.length; i++) {
            switch (operateArr[i].t) {
                case 'mingGang':
                case 'anGang':
                case 'buGang':
                    gangArr.push(i);
                    break;
                case 'chi':
                    chiArr.push(i);
                    break;
                case 'pong':
                    showArr.push('pong');
                    break;
            }
        };

        chiArr.length > 0 && showArr.push('chi');
        gangArr.length > 0 && showArr.push('gang');

        for (var i = 0; i < operateArr.length; i++) {
            switch (operateArr[i].t) {
                case 'youJing': //游金
                case 'shuangYou': //双游
                case 'sanYou': //三游
                case 'sanJingDao': //三金倒
                case 'hu': //点炮胡
                case 'tianHu': //天胡
                case 'ziMo':
                    //自摸胡
                    showArr.push(operateArr[i].t);
                    break;
                //     showArr.push('hu');
                //     break;
            }
        };

        for (var i = 0; i < showArr.length; i++) {
            var operateName = showArr[i]; //'guo' || chi' || 'pong' ||'gang' || 'hu' || 'youJing' || 'shuangYou' || 'sanYou' || 'sanJingDao'

            if (operateName === 'hu' || operateName === 'tianHu' || operateName === 'ziMo') {
                operateName = 'dao';
            }

            this.btnsArr[i].getComponent(cc.Sprite).spriteFrame = this.operate_Atlas.getSpriteFrame(operateName + '_btn'); //设置文字图片
            this.btnsArr[i].active = true; //显示节点
        }

        //隐藏多余的
        for (var j = i; j < this.btnsArr.length; j++) {
            this.btnsArr[j].active = false;
        }

        this.isShowing = true; //选项面板已经显示
        this.btnsNode.active = true; //显示操作选择按钮的面板
    },

    /**
     * 显示要操作的牌
     * @param {Array}  operateArr --
     * @param {Array}  type --
     */
    showChoose: function showChoose(type) {
        if (type !== 'gang' && type !== 'chi') {
            return null;
        }

        var arr = this[type + 'Arr'];

        if (this.currentArr === arr) {
            //如果和上次点的是同一个按钮 如 多次点杠
            this.chooseNode.active = !this.chooseNode.active;
            return arr;
        }

        var operateArr = this.operateArr;
        var totalWidth = 30; //记录总宽度
        if (arr.length > 1) {

            for (var i = 0; i < arr.length; i++) {
                var index = arr[i];
                var children = this.chooseArr[i].children;
                var cardsArr = operateArr[index].m;
                var operateName = operateArr[index].t;

                for (var j = 0; j < cardsArr.length; j++) {
                    children[j].active = true;
                    children[j].getChildByName('hua').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardsArr[j]);

                    if (operateName === 'chi' && operateArr[index].c === cardsArr[j]) {
                        //如果是吃的那张牌
                        children[j].getChildByName('mask').active = true;
                    } else {
                        children[j].getChildByName('mask').active = false;
                    }
                }

                //显示外来的牌
                if (operateName === 'mingGang') {
                    children[j].active = true;
                    children[j].getChildByName('hua').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(operateArr[index].c);
                    children[j].getChildByName('mask').active = true;
                    j++;
                }

                //隐藏多余的
                for (var m = j; m < 4; m++) {
                    children[m].active = false;
                }

                this.chooseArr[i].width = this.mjW * j; //设置每四个麻将的宽度
                totalWidth += this.mjW * j; //宽度累加起来
                this.chooseArr[i].active = true;
            }

            //隐藏多余的
            for (var k = i; k < this.chooseArr.length; k++) {
                this.chooseArr[k].active = false;
            }

            var chooseNodeWidth = totalWidth + (i - 1) * 10; //左侧的选择框长度
            totalWidth = chooseNodeWidth + this.btnsNode.width; //选择框 + 按钮框的长度
            if (totalWidth > 920) {
                this.chooseNode.x = this.btnsNode.x - 100 - chooseNodeWidth / 2;
                this.chooseNode.y = 90;
            } else {
                this.chooseNode.x = this.btnsNode.x - chooseNodeWidth / 2 - this.btnsNode.width;
                this.chooseNode.y = 0;
            }

            this.chooseNode.active = true;
            this.currentArr = arr;
        } else {
            this.chooseNode.active = false;
        }

        return arr;
    },

    /**
     * 设置或获取面板信息
     * @public
    */
    panelAttr: function panelAttr(name, value) {
        switch (name) {
            case 'isShow':
                var isShow = value === 'show' ? true : false;
                this.isShowing = isShow; //选项面板已经隐藏
                this.btnsNode.active = isShow; //隐藏整个选项面板
                this.chooseNode.active = isShow;
                break;
            case 'isCanShowPanel':
                //设置是否能弹出的状态
                if (value !== undefined) {
                    return this.isCanShowPanel = value;
                } else {
                    return this.isCanShowPanel;
                }
            case 'isShowing':
                //取得面板当前的显示状态
                if (value !== undefined) {
                    return this.isShowing = value;
                } else {
                    return this.isShowing;
                }
            case 'gangArr':
                //杠在operate中的位置号
                return this.gangArr;
            case 'chiArr':
                //吃在operate中的位置号
                return this.chiArr;
            case 'showArr':
                //每一个按钮
                return this.showArr;
            case 'currentArr':
                //注意，每次都要重置为 null, 下次才会更新
                if (value !== undefined) {
                    return this.currentArr = value;
                } else {
                    return this.currentArr; //它指向  gangArr 或 chiArr
                }
            case 'showObj':
                return { gangArr: this.gangArr, chiArr: this.chiArr, showArr: this.showArr };
        }
    }

});

module.exports = OperateEvent;

cc._RFpop();
},{}],"hand_outCardEvent":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8f09amlE9tMc7y1HXb6bWQ5', 'hand_outCardEvent');
// scripts\room\view\event\hand_outCardEvent.js

'use strict';

/**@class
 * @classdesc 管理出牌事件的类
 */
var OutCardEvent = cc.Class({

    name: 'OutCardEvent',

    /**
     * @constructor
     * @param {object} userNode
     * }
     */
    ctor: function ctor() {
        var userNode = arguments[0];

        this.handNode = userNode.getChildByName('hand');

        this.clickNum = 0; //记录点击次数
        this.clickNode = null; //用于暂存被点击的那个节点对象

        this.isCanOperate = true; //当听牌时，不可操作，默认是可以操作的
        this.isCanPlay = false; //当没有轮到自己出牌时，可以点出牌，但打不出去

        this.handCardNodes = this.handNode.children; //手中牌的数组

        this._andClick_(); //添加事件
    },

    /**当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        this.clickNum = 0; //记录点击次数
        this.clickNode && (this.clickNode.y = 0); //上次被点击的节点
        this.clickNode = null; //用于暂存被点击的那个节点对象
        this.isCanOperate = true; //当听牌时，不可操作，默认是可以操作的
        this.isCanPlay = false; //当没有轮到自己出牌时，可以点出牌，但打不出去
    },

    /**
     * 当点击空白处时，麻将牌要回到原位
     * 挂载于 Canvas节点上
     * @public
    */
    mahRunBack: function mahRunBack() {
        if (this.clickNode) {
            this.clickNode.y = 0; //上次被点击的节点
            this.clickNode = null;
        }
    },

    /**
     * 访问或设置属性
     * @public
     * @param {string} name -- 属性名 可以传入：
     *                              1. 'isCanOperate': 整个点击事件是否可用
     *                              2. 'isCanPlay': 控制是否可以有点击动作(如：没轮到用户出牌时，可以点击，但不能出牌)
     * @param {boolean} value -- 可省略，属性值。省略时表示访问，不省略时表示更改
     * @returns {boolean} -- 设置的结果 或 访问的属性值
    */
    outCardEventAttr: function outCardEventAttr(name, value) {
        if (value === undefined) {
            return this[name];
        } else {
            return this[name] = value;
        }
    },

    /**
    * 添加出牌的点击事件
    * @private
    */
    _andClick_: function _andClick_() {

        for (var i = 0; i < 17; i++) {
            if ('touches' in cc.sys.capabilities) {
                console.log('outCardEvent--touches');
                this.handCardNodes[i].on('touchstart', this._clickDown_, this);
                this.handCardNodes[i].on('touchend', this._clickUp_, this);
            } else if ('mouse' in cc.sys.capabilities) {
                console.log('outCardEvent--mouse');
                this.handCardNodes[i].on('mousedown', this._clickDown_, this);
                this.handCardNodes[i].on('mouseup', this._clickUp_, this);
            }
        }
    },

    /**
     * 出牌点击时 的按下事件
     * @private
     * @param {Event} event -- 点击时的事件对象
     */
    _clickDown_: function _clickDown_(event) {
        //
        if (!this.isCanOperate) {
            return;
        }
        window.audio.playEffect('select'); //播放点击时的声音

        var node = event.target; //点击的节点
        if (this.clickNode === node) {
            this.clickNum = 2;
        } else {
            if (this.clickNode === null) {
                this.clickNode = node;
                this.clickNum = 1;
                node.runAction(cc.moveBy(0.03, 0, 15));
            } else {
                this.clickNum = 3;
                node.runAction(cc.moveBy(0.03, 0, 15));
                this.clickNode.y = 0;
                this.clickNode = node;
            }
        }
    },

    /**
     * 出牌点击时 的松开事件
     * @private
     * @param {Event} event -- 点击时的事件对象
     */
    _clickUp_: function _clickUp_(event) {
        if (!this.isCanOperate) {
            return;
        }

        var node = event.target; //点击的节点
        if (this.clickNum === 2) {
            if (this.isCanPlay) {
                //如果可以出牌
                this.isCanPlay = false; //禁用出牌动作
                var cardName = clientUser.attr('cardsHandOrder')[node.name]; //点击的牌的名字
                console.log("点击选中的牌是：" + cardName);

                var youJingCards = clientUser.attr('youJingCards');

                if (youJingCards) {
                    if (youJingCards.indexOf(cardName) !== -1) {
                        clientUser.displayHead('youJing', youJingCards.type); //自己显示正在游金中
                        //发送游金的消息
                        clientUser.send({ type: youJingCards.type, message: cardName });
                    } else {
                        //发送出牌消息
                        clientUser.send({ message: cardName, type: 'outCard' });
                    }
                    clientUser.attr('youJingCards', null); //清空游金的牌
                } else {
                    //发送出牌消息
                    clientUser.send({ message: cardName, type: 'outCard' });
                }

                clientUser.outCard(cardName); //出一张牌

                this.clickNode.y = 0;
                this.clickNode = null;
            }
        }
    }

});

module.exports = OutCardEvent;

cc._RFpop();
},{}],"helpText":[function(require,module,exports){
"use strict";
cc._RFpush(module, '75127I329pPn61627elU7iQ', 'helpText');
// scripts\common\helpText.js

"use strict";

/**
 * 帮助弹出框的内容
 */
var helpText = "\u4E00\u3001\u57FA\u672C\u7528\u8BCD\u8BF4\u660E\n    1.\u624B\u724C\uFF1A\u724C\u5C40\u5F00\u59CB\u65F6\u53D1\u7ED9\u81EA\u5DF1\u7684\u724C\uFF0C\u5373\u81EA\u5DF1\u624B\u4E2D\u7684\u6240\u6709\u724C(\u81EA\u5DF1\u53EF\u770B\u89C1\u724C\u7684\u6B63\u9762\uFF0C\u522B\u4EBA\u53EA\u80FD\u770B\u89C1\u724C\u7684\u80CC\u9762)\uFF0C\u5E84\u5BB6\u8D77\u59CB17\u5F20\u624B\u724C\uFF0C\u95F2\u5BB6\u8D77\u59CB16\u5F20\u624B\u724C\u3002\n    2.\u660E\u724C\uFF1A\u5373\u81EA\u5DF1 \u78B0\u3001\u660E\u6760\u3001\u6697\u6760\u3001\u8865\u6760\u3001\u5403 \u540E\uFF0C\u6446\u653E\u5728\u81EA\u5DF1\u624B\u724C\u65C1\u8FB9\u7684\u6240\u6709\u724C\u3002\n    2.\u5E84\u5BB6\uFF1A\u6BCF\u4E00\u5C40\u90FD\u6709\u4E00\u4E2A\u4EBA\u662F\u5E84\u5BB6\uFF0C\u7B2C\u4E00\u5C40\u7684\u5E84\u5BB6\u662F\u623F\u95F4\u7684\u521B\u5EFA\u8005\u3002\u5E84\u5BB6\u8D62\u724C\u6216\u8F93\u724C\u65F6\uFF0C\u8F93\u8D62\u8981\u7FFB\u500D\u3002\n    3.\u95F2\u5BB6\uFF1A\u6BCF\u4E00\u5C40\u90FD\u6709\u4E09\u4E2A\u4EBA\u662F\u95F2\u5BB6\u3002\u5E84\u5BB6\u8D62\u724C\u6216\u8F93\u724C\u65F6\uFF0C\u8F93\u8D62\u6309\u7167\u6B63\u5E38\u7684\u5904\u5206\u6D41\u7A0B\u8BA1\u7B97\u3002\n    4.\u5E8F\u6570\u724C\uFF1A\u6307 1\u7B52~9\u7B52\u30011\u6761~9\u6761\u30011\u4E07~9\u4E07\u3002\n    5.\u7BAD\u724C\uFF1A\u6307 \u7EA2\u4E2D\u3001\u53D1\u8D22\u3001\u767D\u677F\u3002\n    6.\u987A\u5B50\uFF1A\u7B52\u724C\u4E2D\u8FDE\u7EED\u4E09\u5F20\u724C\u53EF\u7EC4\u6210\u4E00\u4E2A\u987A\u5B50\uFF0C\u6761\u724C\u4E2D\u8FDE\u7EED\u4E09\u5F20\u724C\u53EF\u7EC4\u6210\u4E00\u4E2A\u987A\u5B50\uFF0C\u4E07\u724C\u4E2D\u8FDE\u7EED\u4E09\u5F20\u724C\u53EF\u7EC4\u6210\u4E00\u4E2A\u987A\u5B50\uFF0C\n            \u6CE8\u610F \u4E00\u5F20\u7EA2\u4E2D+\u4E00\u5F20\u53D1\u8D22+\u4E00\u5F20\u767D\u677F\u4E0D\u80FD\u7EC4\u6210\u4E00\u4E2A\u987A\u5B50\u3002 \u4E00\u5F20\u4E1C\u98CE+\u4E00\u5F20\u897F\u98CE+\u4E00\u5F20\u5357\u98CE\u4E5F\u4E0D\u80FD\u7EC4\u6210\u4E00\u4E2A\u987A\u5B50\n            \u5982\uFF1A\u4E00\u5F203\u7B52+\u4E00\u5F204\u7B52+\u4E00\u5F205\u7B52\u4E3A\u4E00\u987A\uFF0C\u4E00\u5F203\u7B52+\u4E00\u5F204\u6761+\u4E00\u5F205\u7B52\u4E0D\u662F\u4E00\u987A\u3002\n    7.\u4E00\u5BF9\u6216\u5BF9\u5B50\uFF1A\u6307\u624B\u724C\u4E2D\u4E24\u5F20\u5B8C\u5168\u4E00\u6837\u7684\u724C\u3002\n    8.\u523B\u5B50\uFF1A\u6307\u624B\u724C\u4E2D\u4E09\u5F20\u5B8C\u5168\u4E00\u6837\u7684\u724C\u3002\n    9.\u6760\u5B50\uFF1A\u6307\u624B\u724C\u4E2D\u7684\u56DB\u5F20\u5B8C\u5168\u4E00\u6837\u7684\u724C\u3002\n    10.\u6760\uFF1A\u6307 \u660E\u6760\u3001\u6697\u6760\u3001\u8865\u6760\u3002\n    11.\u81EA\u6478\uFF1A\u81EA\u5DF1\u6478\u4E86\u4E00\u5F20\u724C\u540E\uFF0C\u80E1\u724C\u4E86\uFF0C\u79F0\u4E3A\u81EA\u6478\u3002\n    12.\u653E\u70AE\uFF1A\u81EA\u5DF1\u6253\u4E86\u4E00\u5F20\u724C\u88AB\u522B\u4EBA\u80E1\u4E86\uFF0C\u5C31\u79F0\u81EA\u5DF1 \u653E\u70AE\u3002\n    13.\u63A5\u70AE\uFF1A\u522B\u4EBA\u6253\u4E86\u4E00\u5F20\u724C\u88AB\u81EA\u5DF1\u80E1\u4E86\uFF0C\u5C31\u79F0\u81EA\u5DF1 \u63A5\u70AE\u3002\n    14.\u81ED\u5E84\uFF1A\u81ED\u5E84\u5373\u9EC4\u5E84\uFF0C\u5F53\u724C\u5899\u91CC\u5269\u4E0B16\u5F20\u724C\u65F6\uFF0C\u5982\u679C\u6CA1\u6709\u4EBA\u5230\u724C\uFF0C\u5373\u81ED\u5E84\u3002\u81ED\u5E84\u5F53\u76D8\u7684\u5E84\u5BB6\u7EE7\u7EED\u505A\u4E0B\u4E00\u76D8\u7684\u5E84\u5BB6\uFF0C\u5E84\u5BB6\u7684\u5E95\u8FD9\u65F6\u8981\u7FFB\u500D\uFF0C\u4E58\u4EE52\u500D\uFF0C\u518D\u6B21\u81ED\u5E84\u518D\u4E58\u3002\n    15.\u8FDE\u5E84\uFF1A\u5982\u679C\u5E84\u5BB6\u5230\u724C\u5219\u53D1\u751F\u8FDE\u5E84\uFF0C\u8FDE\u5E84\u7684\u65F6\u5019\u5E95\u5206\u7FFB\u500D\uFF0C\u6CA1\u6709\u6740\u4E09\u3002\n    16.\u6760: \u8865\u6760\u53EF\u4EE5\u62A2\u6760(\u5230), \u6697\u6760\u4E0D\u663E\u793A\u724C(\u53EA\u6709\u81EA\u5DF1\u80FD\u770B\u5230\u81EA\u5DF1\u7684\u6697\u6760\u7684\u724C\uFF0C\u522B\u4EBA\u53EA\u80FD\u770B\u5230\u724C\u7684\u80CC\u9762)\u3002\n    17.\u91D1\u724C: \u4EFB\u610F\u724C\u90FD\u53EF\u80FD\u6210\u4E3A\u91D1\u724C\uFF0C\u7531\u7FFB\u91D1\u4EA7\u751F,\u91D1\u724C\u76F8\u5F53\u4E8E\u6DF7\u724C\uFF0C\u767E\u642D\uFF0C\u5176\u529F\u80FD\u662F\u53EF\u4EE5\u66FF\u4EE3\u9664\u82B1\u724C\u4E4B\u5916\u7684\u4EFB\u4F55\u724C\u3002\u7FFB\u5F00\u7684\u90A3\u5F20\u91D1\u724C\uFF0C\u76F8\u5F53\u4E8E\u6253\u4E86\u4E00\u5F20\u724C\u3002\n            \u91D1\u724C\u53EF\u4EE5\u4E3A\u82B1\u724C\uFF0C\u5F53\u91D1\u724C\u4E3A\u9ED1\u82B1(\u6885\u5170\u7AF9\u83CA)\u65F6\uFF0C\u7EA2\u82B1(\u6625\u590F\u79CB\u51AC)\u9700\u8981\u8865\u724C\uFF0C\u5426\u5219\uFF0C\u9ED1\u82B1\u9700\u8981\u8865\u724C\u3002\n    18.\u7FFB\u91D1\uFF1A\u91D1\u724C\u7684\u4EA7\u751F\u79F0\u4E3A\u7FFB\u91D1\u3002\u5E84\u5BB6\u8865\u724C\u7ED3\u675F\u540E\uFF0C\u8DF3\u724C\u5904\u7684\u4EBA\u6254\u51FA\u4E24\u4E2A\u9AB0\u5B50\uFF0C\u6839\u636E\u70B9\u6570\u4ECE\u724C\u5899\u6700\u540E\u4E00\u5F20\u724C\u6570\u6570\uFF0C\u6700\u540E\u843D\u5728\u54EA\u5F20\u4E0A\u7FFB\u5F00\u5373\u4E3A \u91D1\u3002\n    19.\u5355\u6E38\uFF1A\u5F53\u624B\u4E0A\u62FF\u91D1\u724C\u4F5C\u4E3A\u5C06\u724C\u7684\u65F6\u5019\u53EF\u4EE5\u5C06\u4E00\u5F20\u5C06\u724C\u6253\u51FA\uFF0C\u8FD9\u6837\u5728\u4E0B\u4E00\u5708\u5185\u6478\u5230\u4EFB\u4F55\u724C\u5747\u53EF\u5230\u724C\uFF0C\u6B64\u65F6\u5230\u724C\u79F0\u4E3A\u5355\u6E38\u3002\n    20.\u53CC\u6E38\uFF1A\u5982\u679C\u5355\u6E38\u65F6\u6478\u5230\u7684\u724C\u4E3A\u91D1\u724C\uFF0C\u6B64\u65F6\u53EF\u4EE5\u5C06\u91D1\u724C\u6253\u51FA\uFF0C\u91D1\u724C\u6253\u51FA\u540E\uFF0C\u5F00\u59CB\u53CC\u6E38,\u672C\u5708\u5185\u6240\u6709\u73A9\u5BB6\u90FD\u53EA\u80FD\u81EA\u6478\uFF0C\u4E0D\u80FD\u5E73\u5230(\u70B9\u70AE\u80E1\u6216\u62A2\u6760\u80E1)\u3002\n            \u5F53\u6E38\u91D1\u65F6\u6478\u5230\u7684\u724C\u6B63\u597D\u53EF\u4EE5\u4EE3\u53E6\u4E00\u5F20\u91D1(\u80FD\u5230\u724C\u7684\u60C5\u51B5\u4E0B)\uFF0C\u53EF\u4EE5\u5C06\u91D1\u6253\u51FA\uFF0C\u6B64\u65F6\u5176\u5B83\u4E09\u5BB6\u4E0D\u80FD\u5E73\u5230\uFF0C\u81EA\u5DF1\u5982\u6CA1\u80FD\u81EA\u6478\u53CC\u6E38\u5C31\u6CA1\u8FC7\uFF0C\u5982\u80FD\u81EA\u6478\u5219\u7B97\u6E38\u8FC7\n    21.\u4E09\u6E38\uFF1A\u53CC\u6E38\u65F6\u5982\u679C\u7EE7\u7EED\u6478\u5230\u91D1\u724C\uFF0C\u6B64\u65F6\u4ECD\u65E7\u5C06\u91D1\u724C\u6253\u51FA\uFF0C\u89C6\u4E3A\u4E09\u6E38\uFF0C\u4E09\u6E38\u65F6\u672C\u5708\u5185\u6240\u6709\u73A9\u5BB6\u90FD\u53EA\u80FD\u81EA\u6478\uFF0C\u4E0D\u80FD\u5E73\u5230(\u70B9\u70AE\u80E1\u6216\u62A2\u6760\u80E1)\u3002\n            \u5F53\u53CC\u6E38\u65F6\uFF0C\u6478\u5230\u7684\u724C\u53C8\u662F\u4E00\u5F20\u91D1\uFF0C\u53EF\u4EE5\u518D\u6B21\u5C06\u91D1\u6253\u51FA\uFF0C\u6B64\u65F6\u5176\u5B83\u4E09\u5BB6\u53EA\u80FD\u5404\u6478\u4E00\u5F20\u724C\uFF0C\u5982\u6CA1\u80FD\u81EA\u6478\u4E09\u6E38\u5C31\u6CA1\u8FC7\uFF0C\u5982\u80FD\u81EA\u6478\u5219\u7B97\u6E38\u8FC7\uFF0C\u6B64\u4E3A\u4E09\u6E38\n    22.\u5230: \u5373 \u5230\u724C\uFF0C\u4E5F\u53EB\u80E1\u724C\uFF0C\u201C\u5230\u201D\u548C\u201C\u80E1\u201D\u7684\u610F\u4E49\u5728\u672C\u6587\u6863\u4E2D\u5B8C\u5168\u4E00\u6837\u3002\n    23.\u4E09\u91D1\u5012\uFF1A\u8D77\u624B\u6216\u5728\u81ED\u5E84\u524D\u53EA\u8981\u624B\u91CC\u62FF\u4E09\u4E2A\u91D1\u4E0D\u7BA1\u5176\u4ED6\u724C\u662F\u4EC0\u4E48\u724C\uFF0C\u5373\u53EF\u5230\u724C\u3002\n    24.\u5929\u5230\uFF0C\u8D77\u624B\u5C31\u5230\u724C\u3002\n    25.\u5E73\u5230\uFF1A\u522B\u4EBA\u6253\u51FA\u6765\u7684\u724C\u5176\u4E2D\u4E00\u5BB6\u5230\uFF0C\u79F0\u4E3A\u5E73\u5230\u3002\n    26.\u76D8: \u4E5F\u79F0\u5C40\u3002\u5F53\u76D8(\u5F53\u5C40)\uFF0C\u662F\u6307\u5F53\u524D\u6B63\u5728\u6253\u7684\u4E00\u5C40\u724C\u3002\n\n\u4E8C\u3001\u57FA\u672C\u89C4\u5219\n    1.\u6240\u6709\u7684\u724C\u5171144\u5F20\uFF1A\u5176\u4E2D \u7B52\u724C(\u5171\u8BA136\u5F20)\uFF1A1\u7B52~9\u7B52\uFF0C\u6BCF\u79CD\u724C4\u5F20\n                          \u6761\u724C(\u5171\u8BA136\u5F20)\uFF1A1\u6761~9\u6761\uFF0C\u6BCF\u79CD\u724C4\u5F20\n                          \u4E07\u724C(\u5171\u8BA136\u5F20)\uFF1A1\u4E07~9\u4E07\uFF0C\u6BCF\u79CD\u724C4\u5F20\n                          \u7BAD\u724C(\u5171\u8BA112\u5F20)\uFF1A\u7EA2\u4E2D\u3001\u53D1\u8D22\u3001\u767D\u677F\uFF0C\u54044\u5F20\n                          \u98CE\u724C(\u5171\u8BA116\u5F20)\uFF1A\u4E1C\u3001\u5357\u3001\u897F\u3001\u5317\uFF0C\u54044\u5F20\n                          \u82B1\u724C(\u5171\u8BA18\u5F20)\uFF1A \u6625\u3001\u590F\u3001\u79CB\u3001\u51AC(\u8FD9\u56DB\u5F20\u724C\u79F0\u4E3A\u7EA2\u82B1)\uFF0C\u6885\u3001\u5170\u3001\u7AF9\u3001\u83CA(\u8FD9\u56DB\u5F20\u724C\u79F0\u4E3A\u9ED1\u82B1)\u3002\n    2.\u4E00\u5C40\u724C\u6709\u56DB\u4E2A\u73A9\u5BB6\uFF0C\u6709\u5E84\u5BB6\u548C\u95F2\u5BB6\u4E4B\u5206\u3002\u5176\u4E2D\u4E00\u4E2A\u662F\u5E84\u5BB6\uFF0C\u53E6\u5916\u4E09\u4E2A\u662F\u95F2\u5BB6\uFF0C\u5E84\u5BB6\u8D77\u59CB\u62FF\u724C17\u5F20\uFF0C\u95F2\u5BB6\u8D77\u59CB\u62FF\u724C16\u5F20\u3002\n    3.\u53EF\u4EE5\u78B0\uFF1A\u5373 \u5176\u5B83\u73A9\u5BB6\u51FA\u4E86\u4E00\u5F20\u724C\uFF0C\u800C\u81EA\u5DF1\u624B\u724C\u4E2D\u6709 \u4E0E\u8FD9\u5F20\u724C\u5B8C\u5168\u76F8\u540C\u7684\u724C \u4E24\u5F20\uFF0C\u5219\u81EA\u5DF1\u53EF\u4EE5\u9009\u62E9\u78B0\uFF0C\u78B0\u540E\u8981\u51FA\u4E00\u5F20\u724C\u3002\n    4.\u53EF\u4EE5\u660E\u6760\uFF1A\u5373 \u5176\u5B83\u73A9\u5BB6\u51FA\u4E86\u4E00\u5F20\u724C\uFF0C\u800C\u81EA\u5DF1\u624B\u724C\u4E2D\u6709 \u4E0E\u8FD9\u5F20\u724C\u5B8C\u5168\u76F8\u540C\u7684\u724C \u4E09\u5F20\uFF0C\u5219\u81EA\u5DF1\u53EF\u4EE5\u9009\u62E9\u660E\u6760\uFF0C\u660E\u6760\u540E\u8981\u62FF\u4E00\u5F20\u724C\u3002\n    5.\u53EF\u4EE5\u6697\u6760\uFF1A\u5373 \u73A9\u5BB6\u81EA\u5DF1\u62FF\u4E86\u4E00\u5F20\u724C\u540E\uFF0C\u800C\u81EA\u5DF1\u624B\u724C\u4E2D\u6709 \u56DB\u5F20\u5B8C\u5168\u76F8\u540C\u7684\u724C\uFF0C \u5219\u81EA\u5DF1\u53EF\u4EE5\u9009\u62E9\u6697\u6760\uFF0C\u6697\u6760\u540E\u8981\u62FF\u4E00\u5F20\u724C\u3002\n    6.\u53EF\u4EE5\u8865\u6760\uFF1A\u5373 \u73A9\u5BB6\u81EA\u5DF1\u62FF\u4E86\u4E00\u5F20\u724C\u540E\uFF0C\u800C\u73A9\u5BB6\u81EA\u5DF1\u4EE5\u524D\u78B0\u8FC7\u8FD9\u5F20\u724C\uFF0C\u5219\u73A9\u5BB6\u81EA\u5DF1\u53EF\u4EE5\u9009\u62E9\u8865\u78B0\uFF0C\u8865\u540E\u8981\u62FF\u4E00\u5F20\u724C\u3002\n    7.\u53EF\u4EE5\u5403\uFF1A\u5373 \u5176\u5B83\u73A9\u5BB6\u51FA\u4E86\u4E00\u5F20\u724C\uFF0C\u800C\u81EA\u5DF1\u624B\u4E2D\u6709\u4E24\u5F20\u724C\u80FD\u4E0E\u51FA\u7684\u90A3\u5F20\u724C\u7EC4\u5408\u6210\u987A\u5B50\uFF0C\u5219\u81EA\u5DF1\u53EF\u4EE5\u9009\u62E9\u5403\uFF0C\u5403\u540E\u8981\u51FA\u4E00\u5F20\u724C\u3002\n    8.\u53EF\u4EE5\u8865\u82B1\uFF1A\u5373 \u73A9\u5BB6\u81EA\u5DF1\u6478\u5230\u4E00\u5F20\u82B1\u724C(\u8FD9\u5F20\u82B1\u724C\u4E0D\u662F\u91D1\u724C\u65F6)\uFF0C\u53EF\u4EE5\u8865\u82B1\uFF0C\u8865\u82B1\u662F\u81EA\u52A8\u8FDB\u884C\u7684\uFF0C\u8865\u82B1\u540E\u4F1A\u62FF\u4E00\u5F20\u724C\u3002\n    9.\u6CA1\u6709\u62A5\u542C\u7684\u529F\u80FD\u3002\n    10.\u6CA1\u6709\u4E00\u70AE\u591A\u54CD(\u5373\u5176\u5B83\u73A9\u5BB6\u51FA\u4E86\u4E00\u5F20\u724C\u540E\uFF0C\u53EA\u80FD\u6709\u4E00\u4E2A\u4EBA\u53EF\u4EE5\u80E1\u8FD9\u5F20\u724C)\u3002\n    11.\u5728\u4E00\u5C40\u4E2D\u53EA\u8981\u6709\u73A9\u5BB6\u80E1\u724C\u4E86 \u6216 \u724C\u53EA\u4E58\u4E0B16\u5F20\u65F6\uFF0C\u672C\u5C40\u7ED3\u675F\u3002\n    12.\u73A9\u5BB6\u7684\u5750\u4F4D\u6309\u52A0\u5165\u623F\u95F4\u7684\u5148\u540E\u987A\u5E8F\u987A\u65F6\u9488\u4F9D\u6B21\u786E\u5B9A\u3002\n    13.\u6240\u6709\u73A9\u5BB6\u4EE5\u9006\u65F6\u9488\u65B9\u5411\u7684\u987A\u5E8F\u8F6E\u6D41\u62FF\u724C\u3002\n\n\u4E09\u3001\u57FA\u672C\u5230\u724C\u89C4\u5219\n        1. \u5230\u724C\u7684\u57FA\u672C\u724C\u578B\u6709\u4E14\u4EC5\u6709\u4EE5\u4E0B\u516D\u79CD\uFF1A\n            \uFF081\uFF0911.123.123.123.123.123 \uFF082\uFF0911.123.123.123.123.111 \uFF083\uFF0911.123.123.123.111.111\n            \uFF084\uFF0911.123.123.111.111.111 \uFF085\uFF0911.123.111.111.111.111 \uFF086\uFF0911.111.111.111.111.111\n            \u6CE8: 1) 11\u8868\u793A\u624B\u724C\u4E2D\u4E24\u5F20\u4E00\u6837\u7684\u724C\uFF0C\u5373\u5BF9\u5B50\u3002\u5728\u5230\u724C\u724C\u578B\u4E2D\u4E13\u95E8\u79F0\u5176\u4E3A\u5C06\u724C\u3002\n                2) 111\u8868\u793A\u624B\u724C\u4E2D\u4E09\u5F20\u4E00\u6837\u7684\u724C \u6216 \u660E\u724C\u4E2D\u4E09\u5F20\u6216\u56DB\u5F20\u4E00\u6837\u7684\u724C\u3002\u53EF\u4EE5\u662F\u523B\u5B50\uFF0C\u4E5F\u53EF\u4EE5\u662F\u660E\u724C\u4E2D\u7684\u660E\u6760\u3001\u6697\u6760\u3001\u8865\u6760\u3001\u78B0\u3002\n                3) 123\u8868\u793A\u4E00\u4E2A\u987A\u5B50\u3002\u53EF\u4EE5\u662F\u624B\u724C\u4E2D\u7684\u987A\u5B50\uFF0C\u4E5F\u53EF\u4EE5\u662F\u660E\u724C\u4E2D\u7684\u5403\u3002\n\n        2. \u6BCF\u6B21\u5230\u724C\uFF0C\u53EA\u80FD\u662F\u4EE5\u4E0B\u8FD9\u516D\u79CD\u5230\u724C\u65B9\u5F0F\u4E2D\u7684\u4E00\u79CD(\u6CE8\u610F\uFF0C\u4E0D\u53EF\u80FD\u540C\u65F6\u662F\u4E24\u79CD\u6216\u4EE5\u4E0A)\uFF1A\n            (1)\u5E73\u5230  (2)\u81EA\u6478  (3)\u6E38\u91D1  (4)\u53CC\u6E38  (5)\u4E09\u6E38  (6)\u4E09\u91D1\u5012\n            \u6CE8: \u5F53\u5C40\u7ED3\u675F\u540E\uFF0C\u603B\u662F\u4EE5\u4E0A\u8FF06\u79CD\u4E2D\u7684\u4E00\u79CD\u5230\u724C\u7C7B\u578B\u8BA1\u7B97\u6BCF\u4E2A\u4EBA\u7684\u5F97\u5206\n\n        3.\u53EA\u8981\u6709\u4E00\u4E2A\u4EBA\u80E1\u724C\u4E86\uFF0C\u4E00\u5C40\u5C31\u7ED3\u675F\u3002\n        4.\u6CA1\u6709\u4E00\u70AE\u591A\u54CD\uFF0C\u5982\u679C\u6709\u591A\u4E2A\u4EBA\u540C\u65F6\u80E1\u4E00\u5F20\u724C\uFF0C\u800C\u5F53\u6709\u4EBA\u51FA\u4E86\u8FD9\u5F20\u724C\u540E\uFF0C\u4ECE\u6253\u8FD9\u5F20\u724C\u7684\u4EBA\u5F00\u59CB\u6309\u9006\u9488\u65B9\u5411\u6700\u8FD1\u7684\u90A3\u4E2A\u4EBA\u80E1\u724C\uFF0C\u5176\u5B83\u4EBA\u662F\u4E0D\u80FD\u80E1\u7684\u3002\n        5.\u8352\u5E84\u4E86\u4EC0\u4E48\u90FD\u4E0D\u7B97\uFF0C\u5927\u5BB6\u5206\u6570\u4E0D\u589E\u4E0D\u51CF, \u4E5F\u5C31\u662F\u4E00\u65E6\u8352\u5E84\u4E86\uFF0C\u4E0D\u7BA1\u6709\u6CA1\u6709\u6760\u6216\u91D1\uFF0C\u5927\u5BB6\u90FD\u5F970\u5206\u3002\n\n\u56DB\u3001\u5E84\u5BB6\u89C4\u5219\n    1\uFF0E\u5982\u679C\u662F\u7B2C\u4E00\u5C40\uFF0C\u5219\u7531\u623F\u95F4\u7684\u521B\u5EFA\u8005\u4F5C\u4E3A\u5E84\u5BB6\uFF1B\n    2\uFF0E\u5982\u679C\u5F53\u76D8\u5E84\u5BB6\u80E1\u724C\uFF0C\u5219\u4E0B\u4E00\u5C40\u7684\u5E84\u5BB6\u4E0D\u53D8(\u8FD8\u662F\u4ED6\u81EA\u5DF1), \u79F0\u4E3A\u8FDE\u5E84\uFF0C\u8FDE\u5E84\u7684\u65F6\u5019\u5E95\u5206\u7FFB\u500D\u3002\n    3. \u5982\u679C\u5F53\u76D8\u95F2\u5BB6\u80E1\u724C\uFF0C\u5219\u6309\u9006\u65F6\u65B9\u5411\u8F6E\u6D41\u5750\u5E84\u3002\n    4. \u5982\u679C\u5F53\u76D8\u81ED\u5E84\uFF0C\u5219\u5F53\u76D8\u7684\u5E84\u5BB6\u7EE7\u7EED\u505A\u4E0B\u4E00\u76D8\u7684\u5E84\u5BB6\uFF0C\u5E84\u5BB6\u7684\u5E95\u8FD9\u65F6\u8981\u7FFB\u500D\uFF0C\u518D\u6B21\u81ED\u5E84\u518D\u4E58\u3002\n    5\uFF0E\u5E84\u5BB6\u80E1\u724C\u65F6\uFF0C\u5E84\u5BB6\u8981\u591A\u8D62\u4E00\u756A\uFF0C\u95F2\u5BB6\u80E1\u724C\u65F6\uFF0C\u5E84\u5BB6\u8981\u591A\u8F93\u4E00\u756A\u3002\n\n\u4E94\u3001\u7B97\u5206\u89C4\u5219\n    1.\u6760\u4E0A\u70AE(\u81EA\u5DF1\u6697\u6760\u3001\u8865\u6760\u3001\u8865\u82B1\u540E\uFF0C\u6253\u4E00\u5F20\u724C\uFF0C\u88AB\u522B\u4EBA\u80E1\u4E86)\n      \u6760\u4E0A\u82B1(\u81EA\u5DF1\u6697\u6760\u3001\u8865\u6760\u3001\u8865\u82B1\u540E\uFF0C\u6478\u4E00\u5F20\u724C\uFF0C\u81EA\u5DF1\u80E1\u724C\u4E86)\n      \u5230\u724C\u65F6\uFF0C\u4E0D\u8BBA\u662F\u5426\u662F \u6760\u4E0A\u70AE \u6216 \u6760\u4E0A\u82B1 \u7684\u60C5\u51B5\uFF0C\u5747\u5BF9\u5F97\u5206\u8BA1\u7B97\u4E0D\u4EA7\u751F\u5F71\u54CD\u3002\n    2.\u62A2\u6760(\u4E00\u4E2A\u73A9\u5BB6\u8865\u6760\u65F6\uFF0C\u53E6\u4E00\u4E2A\u73A9\u5BB6\u53EF\u4EE5\u80E1\u4ED6\u8865\u6760\u7684\u724C)\uFF0C\u8FD9\u79CD\u60C5\u51B5\u5BF9\u5F97\u5206\u8BA1\u7B97\u4E0D\u4EA7\u751F\u5F71\u54CD\u3002\n    3.\u5230\u724C\u65F6\u4E0D\u8BBA\u662F\u5426\u662F \u6E05\u4E00\u8272\uFF0C\u5BF9\u5F97\u5206\u8BA1\u7B97\u4E0D\u4EA7\u751F\u5F71\u54CD\u3002\n    4.\u5230\u724C\u65F6\u4E0D\u8BBA\u662F\u7F3A\u95E8\u8FD8\u662F\u4E0D\u7F3A\u95E8\uFF0C\u5BF9\u5F97\u5206\u8BA1\u7B97\u4E0D\u4EA7\u751F\u5F71\u54CD\u3002\n    5.\u5230\u724C\u540E\u5982\u679C\u662F 11.111.111.111.111.111 \u8FD9\u79CD\u724C\u578B\uFF08\u901A\u5E38\u79F0\u4E3A\u5927\u5BF9\u5B50\uFF09, \u5BF9\u5F97\u5206\u8BA1\u7B97\u4E0D\u4EA7\u751F\u5F71\u54CD\u3002\n\n    \u5230\u724C\u5956\u52B1\uFF0C\u724C\u8BA1\u5206\u5982\u4E0B\uFF1A\n        1\u3001\u5E73\u5230\uFF1A1\u7FFB\n        2\u3001\u81EA\u6478\uFF1A2\u7FFB\n        3\u3001\u5355\u6E38 \uFF1A3\u7FFB+\u534A\u4E2A\u5E95\u5206\n        4\u3001\u4E09\u91D1\u5012 \uFF1A2.5\u756A\n        5\u3001\u53CC\u6E38\uFF1A4\u7FFB+\u534A\u4E2A\u5E95\u5206\n        6\u3001\u4E09\u6E38\uFF1A5\u7FFB+\u534A\u4E2A\u5E95\u5206\n        7\u3001\u5929\u5230\uFF1A3\u756A\n        \u6CE8\uFF1A\u5230\u724C\u540E\uFF0C\u9664\u53BB\u5230\u724C\u7684\u4EBA\uFF0C\u5176\u4F59\u672A\u5230\u7684\u4EBA\u4E5F\u8981\u7B97\u756A\u6570\uFF0C\u624B\u4E2D\u7684\u756A\u6570\u76F8\u52A0\u51CF\u3002\n            \u5E84\u5BB6\u7FFB\u500D: \u4E0D\u7BA1\u662F\u4E0D\u662F\u5E84\u5BB6\u70B9\u70AE\u8FD8\u662F\u5230\u724C\u8F93\u8D62\u90FD\u8981\u53CC\u500D\n            \u6760: \u8865\u6760\u548C\u660E\u6760\u3001\u91D1\u90FD\u662F\u4E00\u4E2A\u5E95\u5206, \u6697\u6760\u5E95\u5206*2, \u5230\u7684\u4EBA\u6760,\u4E09\u5BB6\u90FD\u8981\u7ED9, \u6CA1\u5230\u4EBA\u7684\u6760,\u6CA1\u5230\u7684\u4EBA\u7ED9\uFF082\u4E2A\u4EBA\uFF09\n            \u82B1\u6760: \u82B1\u724C\u4E0D\u662F\u91D1\u724C\u724C\u5C40\u7ED3\u675F\u524D\u67094\u4E2A\u53CA\u4EE5\u4E0A\u82B1\u5C31\u53EF\u4EE5\u5F53\u4E00\u4E2A\u6760\uFF0C\u67098\u4E2A\u53CA\u4EE5\u4E0A\u82B1\u5C31\u53EF\u4EE5\u5F53\u4E24\u4E2A\u6760\n            \u5C40\u6570\u6253\u6CD5\u4E2D\u5982\u679C\u8FDE\u5E84,\u5219\u5E95\u5206\u4F9D\u6B21\u7FFB\u500D\u3002\u4F46\u5728\u5206\u6570\u6253\u6CD5\u4E2D\uFF0C\u5982\u679C\u8FDE\u5E84\u5E95\u5206\u4E0D\u7FFB\n\n\u516D\u3001\u6CE8\u610F\u4E8B\u9879\n    1.\u81EA\u5DF1\u6709\u4E00\u5F20\u4E1C\u3001\u4E00\u5F20\u897F\uFF0C\u522B\u4EBA\u6253\u4E00\u5F20\u5357\uFF0C\u81EA\u5DF1\u662F\u4E0D\u53EF\u4EE5\u5403\u7684\uFF0C\u56E0\u4E3A\u98CE\u724C\u6216\u7BAD\u724C\u4E0D\u53EF\u4EE5\u6210\u987A\u3002\n    2.\u53EA\u6709\u81EA\u5DF1\u7684\u4E0A\u5BB6\u6253\u7684\u724C\uFF0C\u81EA\u5DF1\u624D\u53EF\u4EE5\u5403\uFF0C\u5176\u5B83\u4EBA\u6253\u7684\u724C\uFF0C\u81EA\u5DF1\u662F\u4E0D\u80FD\u5403\u7684\u3002\n    3.\u6CA1\u6709\u62A5\u542C\u3002\n    4.\u81EA\u5DF1\u80E1\u724C\u65F6\u6216\u81EA\u5DF1\u6CA1\u80E1\u724C\u65F6\uFF0C\u624B\u4E2D\u6709\u56DB\u4E2A\u4E8C\u4E07\uFF08\u6CA1\u6760\u51FA\u53BB\uFF09\uFF0C\u4E0D\u7B97\u4F5C\u4E00\u6760\uFF0C\u4E5F\u4E0D\u4F1A\u7279\u522B\u7684\u52A0\u5206\u6216\u51CF\u5206\u3002\n    5.\u81EA\u5DF1\u80E1\u724C\u65F6\u6216\u81EA\u5DF1\u6CA1\u80E1\u724C\u65F6\uFF0C\u78B0\u4E86\u4E09\u4E07\uFF0C\u4F46\u624B\u4E2D\u8FD8\u6709\u4E00\u4E2A\u4E09\u4E07\uFF08\u6CA1\u6760\u51FA\u53BB\uFF09\uFF0C\u4E0D\u7B97\u4F5C\u4E00\u6760\uFF0C\u4E5F\u4E0D\u4F1A\u7279\u522B\u7684\u52A0\u5206\u6216\u51CF\u5206\u3002\n    6.\u6CA1\u6709\u6740\u4E09\u3002\n    7.\u6CA1\u6709\u516B\u5F20\u82B1\uFF1A\u6240\u8C13\u201C\u516B\u5F20\u82B1\u201D\u662F\u6307\uFF1A\u8D77\u624B\u6216\u5728\u81ED\u5E84\u524D\u53EA\u8981\u80FD\u591F\u62FF\u5230\u516B\u5F20\u82B1\u724C\uFF0C\u4E0D\u7BA1\u5176\u4ED6\u724C\u662F\u4EC0\u4E48\u724C\uFF0C\u5373\u53EF\u80E1\u724C\uFF0C\u516B\u5F20\u82B1\u6309\u6E38\u91D1\u8BA1\u7B97\u3002\n    8.\u5E73\u80E1\u5C31\u662F\u70B9\u70AE\u80E1\u6216\u62A2\u6760\u80E1\u3002\n\n    9.\u82B1\u724C\u662F\u4E0D\u4F1A\u5B58\u5728\u4E8E\u624B\u724C\u4E2D\u7684(\u56E0\u4E3A\u53EA\u8981\u6478\u5230\u82B1\u724C\uFF0C\u5C31\u5FC5\u987B\u9A6C\u4E0A\u8865\u724C)\uFF0C\u5982\u679C\u91D1\u724C\u662F\u82B1\u724C\uFF0C\u5219\u53EF\u4EE5\u653E\u5728\u624B\u4E2D\uFF0C\u800C\u4E0D\u80FD\u5F53\u4F5C\u82B1\u724C\u8865\u724C\u51FA\u53BB\u3002\n    10.\u5173\u4E8E\u82B1\u6760\uFF1A\n        1\uFF09\u5982\u679C\u91D1\u724C\u662F\u6625\uFF0C\u624B\u4E2D\u6709\u6625\u3001\u590F\uFF0C\u5E76\u4E14\u5DF2\u7ECF\u8865\u82B1\u4E86\uFF1A\u7AF9\u3001\u83CA\uFF0C\u8FD9\u79CD\u60C5\u51B5\u4E0D\u80FD\u7B97\u4F5C\u4E00\u4E2A\u82B1\u6760\u3002\n        2\uFF09\u5982\u679C\u91D1\u724C\u662F\u4E8C\u4E07\uFF0C\u5E76\u4E14\u5DF2\u7ECF\u8865\u82B1\u4E86\uFF1A\u7AF9\u3001\u83CA\u3001\u6625\u3001\u590F\u3001\u79CB\u3001\u51AC\uFF0C\u53EA\u7B97\u4F5C\u4E00\u4E2A\u82B1\u6760(\u800C\u4E0D\u662F\u4E09\u4E2A\u6216\u5176\u5B83\uFF0C\u56E0\u4E3A\u73B0\u5728\u67095\u5F20\u82B1\u724C\uFF0C\u4E0D\u4F1A\u52A0\u756A)\n        3\uFF09\u5982\u679C\u91D1\u724C\u662F\u4E8C\u4E07\uFF0C\u5E76\u4E14\u5DF2\u7ECF\u8865\u82B1\u4E86\uFF1A\u7AF9\u3001\u83CA\u3001\u6625\u3001\u590F\uFF0C\u7B97\u4F5C\u4E00\u4E2A\u82B1\u6760\u3002\n        4\uFF09\u5982\u679C\u91D1\u724C\u662F\u4E8C\u4E07\uFF0C\u5E76\u4E14\u5DF2\u7ECF\u8865\u82B1\u4E86\uFF1A\u7AF9\u3001\u83CA\uFF0C\u8FD9\u6837\u7684\u60C5\u51B5\u4E0D\u7B97\u82B1\u6760\uFF0C\u4E0D\u4F1A\u52A0\u756A\uFF0C\u4E5F\u5C31\u662F\u8BF4\u4E0D\u4F1A\u5BF9\u6700\u7EC8\u7684\u7B97\u5206\u6709\u4EFB\u4F55\u5F71\u54CD\u3002\n    11.\u5173\u4E8E\u80E1\u724C\uFF1A\n        1).\u6CA1\u6709\u5C06\u724C\uFF0C\u5C31\u4E0D\u53EF\u80FD\u80E1\u724C (\u5373\u4E0D\u5B58\u5728\u7C7B\u4F3C\u5047\u80E1\u3001\u70C2\u724C\u7684\u80E1\u724C\u60C5\u51B5).\n        2).\u4E1C\u3001\u5357\u3001\u5317\u6216\u4E2D\u3001\u53D1\u3001\u767D \u7B97\u4F5C\u4E00\u4E2A\u987A\u5B50\uFF0C\u4F46\u4E1C\u3001\u5357\u3001\u4E2D\u4E0D\u7B97\u4F5C\u4E00\u987A\u5B50\u3002\uFF08\u5BF9\uFF0C\u5B57\u724C\u53EA\u80FD\u7528\u6765\u78B0\u3001\u7528\u4F5C\u523B\u5B50\u3001\u7528\u6765\u660E\u6760\u6216\u6697\u6760\u3001\u80E1\u724C\uFF0C\u800C\u4E0D\u80FD\u6709\u5403\u6216\u7528\u4F5C\u987A\u5B50\uFF09\n        3).\u5927\u5BF9\u5B50\u80E1\u724C\u4E0D\u7B97\u7FFB\uFF0811.111.111.111.111.111\u8FD9\u79CD\u724C\u578B\uFF09, \u5373\u6CA1\u6709\u5927\u5BF9\u5B50\u8FD9\u4E00\u8BF4\u3002\n\u4E03\u3001\u5173\u4E8E\u91D1\u724C\n    1.\u5982\u679C\u91D1\u724C\u662F\u6885\uFF0C\u5219\u6885\u3001\u5170\u3001\u7AF9\u3001\u83CA\u90FD\u662F\u91D1\u724C\uFF0C\u6B64\u65F6\u6885\u3001\u5170\u3001\u7AF9\u3001\u83CA\u4E0D\u53EF\u4EE5\u8865\u82B1\uFF0C\u800C\u6625\u3001\u590F\u3001\u79CB\u3001\u51AC\u7B97\u4F5C\u82B1\u724C\uFF0C\u5FC5\u987B\u8865\u82B1(\u5373\u53EA\u8981\u6478\u5230\u5C31\u81EA\u52A8\u8865\u51FA\u53BB\uFF0C\u800C\u4E0D\u80FD\u653E\u5728\u624B\u4E2D)\n    2.\u5982\u679C\u91D1\u724C\u662F\u6625\uFF0C\u5219\u6625\u3001\u590F\u3001\u79CB\u3001\u51AC\u90FD\u662F\u91D1\u724C\uFF0C\u6B64\u65F6\u6625\u3001\u590F\u3001\u79CB\u3001\u51AC\u4E0D\u53EF\u4EE5\u8865\u82B1\uFF0C\u800C\u6885\u3001\u5170\u3001\u7AF9\u3001\u83CA\u7B97\u4F5C\u82B1\u724C\uFF0C\u5FC5\u987B\u8865\u82B1(\u5373\u53EA\u8981\u6478\u5230\u5C31\u81EA\u52A8\u8865\u51FA\u53BB\uFF0C\u800C\u4E0D\u80FD\u653E\u5728\u624B\u4E2D)\n    3.\u91D1\u724C\u662F\u4E09\u4E07\uFF0C\u5982\u679C\u624B\u4E2D\u4E24\u4E2A\u4E8C\u7B52\uFF0C\u4E24\u4E2A\u91D1\u724C(\u4E09\u4E07)\uFF0C\u91D1\u724C\u4E0D\u53EF\u4EE5\u5F53\u4E8C\u7B52\u7528\uFF0C\u6240\u4EE5\u662F\u4E0D\u80FD\u8FDB\u884C\u6697\u6760\u7684\u3002\u7C7B\u4F3C\u7684\u8FD8\u6709\u78B0\u3001\u5403\u3001\u8865\u6760\u7684\u60C5\u51B5\u4E5F\u90FD\u662F\u4E0D\u53EF\u4EE5\u7684\u3002\n      \u91D1\u724C\u53EA\u80FD\u7528\u5728\u66FF\u4EE3\u81EA\u5DF1\u7684\u724C\u6765\u8BA1\u7B97\u80E1\u724C\uFF0C \u8FD8\u53EF\u7528\u4F5C\u6E38\u91D1\uFF0C \u4E0D\u53EF\u4F5C\u5B83\u7528\u3002\n    4.\u91D1\u724C\u662F\u4E09\u4E07\uFF0C\u5982\u679C\u624B\u4E2D\u6709\u4E24\u4E2A\u4E09\u4E07\uFF0C\u522B\u4EBA\u6253\u4E00\u5F20\u4E09\u4E07\uFF0C\u81EA\u5DF1\u53EF\u4EE5\u78B0\uFF0C\u4F46\u78B0\u4E86\u4EE5\u540E\uFF0C\u5C31\u53EA\u80FD\u7B97\u4F5C\u666E\u901A\u724C\u4E86\uFF08\u4E0D\u518D\u5C06\u8FD9\u51E0\u5F20\u91D1\u724C\u7B97\u4F5C\u4E00\u6760\uFF09\u3002\n    5.\u73A9\u5BB6\u81EA\u5DF1\u6253\u4E00\u5F20\u91D1\u724C\uFF0C\u5176\u5B83\u73A9\u5BB6\u662F\u4E0D\u80FD\u80E1\u8FD9\u5F20\u724C\u7684\u3002\n    6.\u81EA\u5DF1\u4E00\u65E6\u5C06\u91D1\u724C\u6253\u51FA\u53BB\u4E86(\u5305\u62EC\u6E38\u91D1\u65F6)\uFF0C\u6700\u540E\u81EA\u5DF1\u7B97\u5206\u65F6\uFF0C\u5C31\u4E0D\u4F1A\u518D\u7B97\u8FD9\u5F20\u91D1\u724C\u7684\u5206\u4E86\uFF0C\u800C\u4E14\u5373\u4F7F\u6E38\u91D1\u6210\u529F\u4E86\uFF0C\u4E5F\u4E0D\u518D\u7B97\u90A3\u4E00\u5F20\u6253\u51FA\u53BB\u7684\u91D1\u724C\u3002\n    8.\u91D1\u724C\u53EA\u6709\u88AB\u653E\u5728\u624B\u724C\u4E2D\u624D\u7B97\u4E00\u6760\uFF0C\u5982\u679C\u78B0\u51FA\u53BB\u6216\u6253\u51FA\u53BB\uFF0C\u5C31\u4E0D\u518D\u7B97\u4E00\u6760\u3002\n    7.\u5982\u679C\u91D1\u724C\u662F\u4E09\u4E07\uFF1A\n        1\uFF09\u81EA\u5DF1\u6709\u624B\u4E2D\u6709\u56DB\u4E07\u3001\u4E94\u4E07\uFF0C\u81EA\u5DF1\u7684\u4E0A\u5BB6\u6253\u4E00\u5F20\u4E09\u4E07\uFF0C\u81EA\u5DF1\u662F\u4E0D\u53EF\u4EE5\u5403\u7684\u3002\n        2\uFF09\u81EA\u5DF1\u5DF2\u7ECF\u78B0\u4E86\u4E09\u4E07\uFF0C\u81EA\u5DF1\u4EE5\u540E\u662F\u4E0D\u53EF\u80FD\u518D\u6478\u5230\u4E09\u4E07\u7684\uFF0C\u56E0\u4E3A\u91D1\u724C\u53EA\u4F1A\u6709\u4E09\u5F20\u724C\uFF0C\u7FFB\u5F00\u7684\u90A3\u5F20\u91D1\u724C\uFF0C\u76F8\u5F53\u4E8E\u6253\u4E86\u4E00\u5F20\u724C\u3002\n        5\uFF09\u81EA\u5DF1\u80FD\u80E1\u4E09\u4E07\u548C\u516D\u4E07\uFF0C\u5F53\u522B\u4EBA\u6253\u4E86\u4E00\u5F20\u4E09\u4E07\u540E\uFF0C\u81EA\u5DF1\u662F\u4E0D\u53EF\u4EE5\u80E1\u7684\u3002\n\n\n\u516B\u3001\u5173\u4E8E\u5355\u6E38\n    1.\u5355\u6E38\u7684\u524D\u63D0\u662F\u81EA\u5DF1\u5DF2\u7ECF\u80E1\u724C\u4E86\uFF0C\u4F46\u662F\u56E0\u4E3A\u81EA\u5DF1\u624B\u4E2D\u7684\u5C06\u724C\u4E2D\u6709\u4E00\u5F20\u662F\u91D1\u724C\uFF0C\u6240\u4EE5\u81EA\u5DF1\u53EF\u4EE5\u4E0D\u9009\u62E9\u80E1\uFF0C\u800C\u8FDB\u884C\u5355\u6E38\u3002\n    2.\u5982\u679C\u81EA\u5DF1\u5DF2\u7ECF\u662F\u80E1\u724C\u4E86\uFF0C\u5E76\u4E14\u5C06\u724C\u4E2D\u4E24\u5F20\u724C\u90FD\u662F\u91D1\u724C\uFF0C\u5219\u6253\u51FA\u4E00\u5F20\u91D1\u724C\uFF0C\u5C31\u8DF3\u8FC7\u4E86\u5355\u6E38\u800C\u76F4\u63A5\u8FDB\u884C\u53CC\u6E38\u3002\n    3.\u5982\u679C\u5C06\u724C\u4E2D\u6070\u597D\u6709\u4E00\u5F20\u724C\u662F\u91D1\u724C\uFF0C\u5219\u6253\u51FA\u8FD9\u5F20\u91D1\u724C\uFF0C\u5E76\u4E14\u771F\u7684\u5728\u4E0B\u4E00\u5708\u5185\u80E1\u724C\u4E86\uFF0C\u4E0D\u7B97\u4F5C\u5355\u6E38\uFF0C\u56E0\u4E3A\u5355\u6E38\u65F6\uFF0C\u624B\u4E2D\u5FC5\u987B\u8981\u6709\u81F3\u5C11\u4E00\u5F20\u91D1\u724C\u3002\n    4.\u5982\u679C\u5C06\u724C\u4E2D\u6070\u597D\u6709\u4E00\u5F20\u724C\u662F\u91D1\u724C\uFF0C\u5219\u6253\u51FA\u53E6\u4E00\u5F20\u724C\uFF0C\u5E76\u4E14\u771F\u7684\u5728\u4E0B\u4E00\u5708\u5185\u80E1\u724C\u4E86\uFF0C\u7B97\u4F5C\u5355\u6E38\u3002\n    5.\u5355\u6E38\u3001\u53CC\u6E38\u3001\u4E09\u6E38\u4E2D\u7684\u2018\u4E00\u5708\u2019\u662F\u6307\uFF1A\n        \u4ECE\u81EA\u5DF1\u6E38\u91D1\u5F00\u59CB\uFF0C\u76F4\u5230\u81EA\u5DF1\u4E0B\u6B21\u6478\u724C\uFF0C\u800C\u4E0D\u8BBA\u5176\u5B83\u4EBA\u6478\u4E86\u51E0\u5F20\u724C\u3002\u4E5F\u5C31\u662F\u8BF4\u4ECE\u81EA\u5DF1\u6E38\u91D1\u51FA\u4E86\u4E00\u5F20\u724C\u540E\uFF0C\u4E0D\u7BA1\u662F\u4EC0\u4E48\u539F\u56E0\u53EA\u8981\u81EA\u5DF1\u6709\u673A\u4F1A\u518D\u6478\u4E00\u5F20\u724C\uFF0C\u5C31\u7B97\u6E38\u4E86\u4E00\u5708\u3002\n\n        \u5E94\u6CE8\u610F\u4EE5\u4E0B\u51E0\u70B9\uFF1A\n            1.\u6E38\u91D1\u5F00\u59CB\u540E\uFF0C\u73A9\u5BB6\u81EA\u5DF1\u53EF\u4EE5\u660E\u6760\u5176\u5B83\u73A9\u5BB6\u7684\u724C\uFF0C\u8FD9\u6837\u53EF\u4EE5\u63D0\u524D\u5B8C\u6210\u5E76\u6210\u529F\u6E38\u91D1\uFF0C\u56E0\u4E3A\u660E\u6760\u540E\u81EA\u5DF1\u4F1A\u6478\u4E00\u5F20\u724C\u3002\n            2.\u6E38\u91D1\u5F00\u59CB\u540E\uFF0C\u73A9\u5BB6\u81EA\u5DF1\u53EF\u4EE5\u78B0\u3001\u5403\uFF0C \u8FD9\u6837\u4E0D\u7B97\u6E38\u91D1\u5931\u8D25\uFF0C\u800C\u662F\u8BA4\u4E3A\u6E38\u91D1\u8FD8\u6CA1\u5B8C\u6210\uFF0C\u4E00\u76F4\u5230\u81EA\u5DF1\u6478\u724C\uFF0C\u624D\u7B97\u6E38\u91D1\u6210\u529F\u3002\n            3.\u6E38\u91D1\u5F00\u59CB\u540E\uFF0C\u5176\u5B83\u73A9\u5BB6\u53EF\u4EE5\u5403\u3001\u78B0\u3001\u660E\u6760\u3001\u6697\u6760\u3001\u8865\u82B1\u3001\u8865\u6760\u3002\n            4.\u5355\u6E38\u5F00\u59CB\u540E\uFF0C\u5219\u5176\u5B83\u73A9\u5BB6\u53EF\u4EE5\u70B9\u70AE\u80E1\u3001\u62A2\u6760\u80E1\u3001\u81EA\u6478\u80E1\uFF0C\u5176\u5B83\u73A9\u5BB6\u5E94\u8BE5\u8DDF\u6CA1\u6709\u6E38\u91D1\u4E00\u6837\uFF0C\u90FD\u53EF\u4EE5\u80E1\uFF0C\u56E0\u4E3A\u522B\u4EBA\u5E76\u4E0D\u77E5\u9053\u4F60\u6B63\u5728\u5355\u6E38\u3002\n            5.\u5982\u679C\u662F\u53CC\u6E38\u548C\u4E09\u6E38\u7684\u6E38\u91D1\uFF0C\u5219\u5176\u5B83\u73A9\u5BB6\u4E0D\u80FD\u70B9\u70AE\u80E1\u3001\u62A2\u6760\u80E1\uFF0C\u800C\u53EA\u80FD\u81EA\u6478\u80E1\u3002\n            6.\u5F53\u73A9\u5BB6\u6E38\u91D1\u6210\u529F\u65F6\uFF0C\u73A9\u5BB6\u81EA\u5DF1\u4E5F\u53EF\u4EE5\u6697\u6760\u3001\u8865\u6760\u3001\u8865\u82B1\uFF0C\u7136\u540E\u518D\u80E1\u724C\uFF0C\u4E5F\u7B97\u4F5C\u6E38\u91D1\u6210\u529F\u3002\n            7.\u5F53\u73A9\u5BB6\u81EA\u5DF1\u6E38\u91D1\u65F6\uFF0C\u5176\u5B83\u73A9\u5BB6\u4E0D\u9700\u8981\u77E5\u9053\u4ED6\u6B63\u5728\u6E38\u91D1\uFF0C\u800C\u4E14\u5176\u5B83\u73A9\u5BB6\u4E5F\u4E0D\u77E5\u9053\u4ED6\u6B63\u5728\u6E38\u91D1\u3002\n            8.\u81EA\u5DF1\u5728\u6E38\u91D1\u65F6\uFF0C\u522B\u4EBA\u6253\u4E00\u5F20\u724C\uFF0C\u81EA\u5DF1\u53EF\u4EE5\u80E1\uFF0C\u4F46\u662F\u80E1\u724C\u540E\uFF0C\u7B97\u4F5C\u666E\u901A\u7684\u80E1\u724C\u800C\u4E0D\u662F\u6E38\u91D1\u3002\n            9.\u5982\u679C\u81EA\u5DF1\u6E38\u91D1\u5931\u8D25\uFF0C\u81EA\u5DF1\u4E0D\u4F1A\u591A\u8F93\u5206\uFF0C\u6309\u7167\u6B63\u5E38\u7684\u7B97\u5206\u903B\u8F91\u8FDB\u884C\u7B97\u5206\u3002\n\n\u4E5D\u3001\u5173\u4E8E\u53CC\u6E38\u548C\u4E09\u6E38\n    1.\u5982\u679C\u81EA\u5DF1\u6709\u4E24\u4E2A\u91D1\u724C\uFF0C\u53EF\u4EE5\u8DF3\u8FC7\u5355\u6E38\u5E76\u76F4\u63A5\u8FDB\u884C\u53CC\u6E38\u3002\n    2.\u5982\u679C\u5355\u6E38\u65F6\u6478\u5230\u7684\u724C\u4E0D\u662F\u91D1\u724C\uFF0C\u4E5F\u6709\u53EF\u80FD\u8FDB\u884C\u53CC\u6E38\uFF0C\u53EA\u8981\u81EA\u5DF1\u8FD8\u80FD\u6253\u51FA\u91D1\u724C\u5E76\u80FD\u80E1\u4EFB\u610F\u724C\uFF0C\u5C31\u53EF\u4EE5\u8FDB\u884C\u53CC\u6E38\u3002\n    3.\u5355\u6E38\u65F6\u6478\u5230\u7684\u724C\u662F\u82B1\u724C\uFF0C\u53EF\u4EE5\u8865\u82B1\u540E\u518D\u8FDB\u884C\u53CC\u6E38\u3002\n    4.\u5355\u6E38\u6210\u529F\u540E\uFF0C\u81EA\u5DF1\u53EF\u4EE5\u8FDB\u884C\u8865\u6760\u3001\u6697\u6760\u3001\u8865\u82B1\uFF0C\u7136\u540E\u8FDB\u884C\u53CC\u6E38\u3002\n    5.\u5982\u679C\u5355\u6E38\u65F6\u6478\u5230\u7684\u724C\u4E0D\u662F\u91D1\u724C\uFF0C\u6BD4\u5982\u662F\u6478\u5230\u56DB\u7B52\uFF0C\u4F46\u624B\u4E2D\u724C\u5176\u5B83\u5730\u65B9\u7528\u91D1\u724C\u4EE3\u66FF\u4E86\u56DB\u7B52\uFF0C\u6B64\u65F6\u5C31\u53EF\u4EE5\u5C06\u91D1\u724C\u6253\u51FA\uFF0C\u8FDB\u884C\u53CC\u6E38\u3002\n    6.\u6253\u51FA\u7684\u724C\u53EA\u80FD\u662F\u91D1\u724C\uFF0C\u624D\u80FD\u53CC\u6E38\uFF0C\u800C\u4E0D\u8BBA\u8FD9\u5F20\u91D1\u724C\u662F\u4E0D\u662F\u521A\u521A\u6478\u5230\u7684(\u6709\u53EF\u80FD\u521A\u624D\u6478\u5230\u7684\u4E0D\u662F\u91D1\u724C\uFF0C\u6216\u81EA\u5DF1\u8865\u6760\u6216\u6697\u6760\u4E86\u518D\u6B21\u6478\u7684\u724C)\u3002\n    7.\u4E09\u6E38\u7684\u60C5\u51B5\u548C\u53CC\u6E38\u7684\u4E00\u6A21\u4E00\u6837\u3002\n    8.\u6700\u591A\u53EA\u4F1A\u6709\u4E09\u6E38\uFF0C\u56E0\u4E3A\u4E00\u5171\u6709\u56DB\u5F20\u91D1\u724C\uFF0C\u800C\u5728\u7FFB\u91D1\u65F6\uFF0C\u5DF2\u7ECF\u7FFB\u4E86\u4E00\u5F20(\u7528\u4E86\u4E00\u5F20)\uFF0C\u8FD8\u5269\u4E0B\u4E09\u5F20\u91D1\u724C\u3002\n\n\u5341\u3001\u5173\u4E8E\u623F\u95F4\n    \u5728\u521B\u5EFA\u623F\u95F4\u65F6\uFF0C\u53EF\u4EE5\u9009\u62E9\u73A9\u6CD5\uFF1A\n    1. 8\u5C40\u7684\u623F\u95F4\uFF1A \u6CA1\u6709\u8FDE\u5E84\u7B97\u6253\u4E86\u4E00\u5C40\uFF0C\u8FDE\u5E84\u4E86\u4E5F\u7B97\u6253\u4E86\u4E00\u5C40\uFF0C \u9EC4\u5E84\u4E86\u8FD8\u662F\u7B97\u6253\u4E86\u4E00\u5C40\u3002\n                  \u4E5F\u5C31\u662F\u8BF4\uFF0C\u53EA\u8981\u4E00\u5C40\u7ED3\u675F\uFF0C\u5C31\u7B97\u6253\u4E868\u5C40\u4E2D\u7684\u4E00\u5C40\u3002\u5B9E\u9645\u6253\u7684\u5C40\u6570\u8FBE\u52308\u5C40\u540E\uFF0C\u6574\u4E2A\u623F\u95F4\u7ED3\u675F\u3002\n    2. 16\u5C40\u7684\u623F\u95F4\uFF1A \u542B\u4E49\u4E0E8\u5C40\u7684\u623F\u95F4\u7C7B\u4F3C\u3002\n    3. 100\u5206\u7684\u623F\u95F4\uFF1A \u662F\u6307\u4E0D\u7BA1\u5B9E\u9645\u6253\u4E86\u591A\u5C11\u5C40\uFF0C\u53EA\u8981\u672C\u5C40\u4E2D\u7684\u56DB\u4E2A\u73A9\u5BB6\u4E2D\u6709\u4E00\u4E2A\u73A9\u5BB6\u8F93\u7684\u5206\u6570\u5927\u4E8E100\u5206\uFF0C\u6574\u4E2A\u623F\u95F4\u5C31\u7ED3\u675F\u4E86\u3002\n    3. 200\u5206\u7684\u623F\u95F4\uFF1A \u542B\u4E49\u4E0E100\u5206\u7684\u623F\u95F4\u7C7B\u4F3C\u3002\n    4. \u5355\u91D1\u4E0D\u80FD\u5E73\u5230\u7684\u623F\u95F4\uFF1A \u6307\u7684\u662F\u5F53\u73A9\u5BB6\u624B\u4E2D\u6070\u597D\u6709\u4E00\u5F20\u91D1\u724C\u65F6\uFF0C\u4E0D\u80FD\u4EE5\u5E73\u5230\u7684\u65B9\u5F0F\u80E1\u724C\u3002\u6CE8\u610F\uFF0C\u8FD9\u91CC\u7684\u4E00\u5F20\u91D1\u724C\uFF0C\u5305\u62EC\u4E86\u80E1\u7684\u90A3\u5F20\u724C\u3002\u6BD4\u5982\uFF0C\u5F53\u624B\u4E2D\u6CA1\u6709\u91D1\u724C\u65F6\uFF0C\u662F\u4E0D\u80FD\u4EE5\u5E73\u5230\u7684\u65B9\u5F0F\u80E1\u4E00\u5F20\u91D1\u724C\u7684\u3002\n                          (\u5982\u679C\u6CA1\u6709\u91D1\u724C\u6216\u521A\u597D\u6709\u4E8C\u5F20\u91D1\u724C\u6216\u521A\u597D\u6709\u4E09\u5F20\u91D1\u724C\uFF0C\u8FD8\u662F\u53EF\u4EE5\u5E73\u5230\u7684)\n    5. \u53CC\u91D1\u4E0D\u80FD\u5E73\u5230\u7684\u623F\u95F4\uFF1A \u6307\u7684\u662F\u5F53\u73A9\u5BB6\u624B\u4E2D\u6070\u597D\u6709\u4E8C\u5F20\u91D1\u724C\u65F6\uFF0C\u4E0D\u80FD\u4EE5\u5E73\u5230\u7684\u65B9\u5F0F\u80E1\u724C\u3002(\u5982\u679C\u6CA1\u6709\u91D1\u724C\u6216\u521A\u597D\u6709\u4E00\u5F20\u91D1\u724C\u6216\u521A\u597D\u6709\u4E09\u5F20\u91D1\u724C\uFF0C\u8FD8\u662F\u53EF\u4EE5\u5E73\u5230\u7684)\n\n\u5341\u4E00\u3001\u5173\u4E8E\u9EC4\u5E84\u548C\u4E09\u91D1\u5012\n    1.\u5982\u679C\u81EA\u5DF1\u662F\u95F2\u5BB6\uFF0C\u521A\u62FF\u5230\u724C\u65F6\uFF0C\u624B\u4E2D\u6709\u4E09\u4E2A\u91D1\uFF0C\u53EA\u80FD\u7B49\u81EA\u5DF1\u6478\u724C\u540E\uFF0C\u518D\u80E1\u724C(\u4EE5\u4E09\u91D1\u5012\u7684\u65B9\u5F0F)\u3002\n    2.\u5982\u679C\u5DF2\u7ECF\u9EC4\u5E84\u4E86\uFF0C\u5C31\u7B97\u81EA\u5DF1\u624B\u4E2D\u6709\u4E09\u4E2A\u91D1\u724C\uFF0C\u4E5F\u4E0D\u80FD\u80E1\u4E86\u3002\n    3.\u5982\u679C\u6700\u540E\u4E00\u5F20\u724C\u662F\u81EA\u5DF1\u62FF\u7684(\u81EA\u5DF1\u6478\u4E86\u724C\u540E\uFF0C\u8FD8\u526916\u5F20\u724C)\uFF0C\u800C\u81EA\u5DF1\u624B\u4E2D\u6709\u4E09\u5F20\u91D1\u724C\uFF0C\u81EA\u5DF1\u53EF\u4EE5\u80E1\u6216\u4E09\u91D1\u5012\u3002\n    4.\u5982\u679C\u6700\u540E\u4E00\u5F20\u724C\u662F\u81EA\u5DF1\u62FF\u7684(\u81EA\u5DF1\u6478\u4E86\u724C\u540E\uFF0C\u8FD8\u526916\u5F20\u724C)\uFF0C\u800C\u81EA\u5DF1\u6B64\u65F6\u4E0D\u80FD\u80E1\u724C\uFF0C\u6253\u4E00\u5F20\u724C\u540E\uFF0C\u5982\u679C\u522B\u4EBA\u80FD\u80E1\u3001\u78B0\u3001\u660E\u6760\uFF0C\u5219\u522B\u4EBA\u53EF\u4EE5\u80E1\u3001\u78B0\u3001\u6760\u3002\n      \u6760\u4E86\u62FF\u4E0D\u4E86\u724C\uFF0C\u4E5F\u4E0D\u7528\u51FA\u724C\uFF0C\u672C\u5C40\u5C31\u7ED3\u675F\u4E86\u3002\u5982\u679C\u78B0\u4E86\uFF0C\u5FC5\u987B\u51FA\u4E00\u5F20\u724C\uFF0C\u5176\u5B83\u73A9\u5BB6\u4E5F\u80FD\u8FDB\u884C\u80E1\u3001\u78B0\u3001\u660E\u6760\u3002\n    5.\u5982\u679C\u6700\u540E\u4E00\u5F20\u724C\u662F\u81EA\u5DF1\u62FF\u7684(\u81EA\u5DF1\u6478\u4E86\u724C\u540E\uFF0C\u8FD8\u526916\u5F20\u724C)\uFF0C\u800C\u81EA\u5DF1\u624B\u4E2D\u8FD8\u6709\u8865\u6760\u3001\u6697\u6760\u7684\u724C\uFF0C\u5219\u81EA\u5DF1\u4E0D\u80FD\u8FDB\u884C\u6697\u6760\u3001\u8865\u6760\uFF0C\u53EA\u662F\u6760\u62FF\u4E0D\u4E86\u724C\uFF0C\u4E5F\u4E0D\u7528\u51FA\u724C\uFF0C\u672C\u5C40\u5C31\u7ED3\u675F\u4E86\u3002\n    6.\u5982\u679C\u6700\u540E\u4E00\u5F20\u724C\u662F\u81EA\u5DF1\u62FF\u7684(\u81EA\u5DF1\u6478\u4E86\u724C\u540E\uFF0C\u8FD8\u526916\u5F20\u724C)\uFF0C\u800C\u81EA\u5DF1\u6070\u597D\u6478\u5230\u4E00\u5F20\u82B1\u724C\uFF0C\u81EA\u5DF1\u662F\u53EF\u4EE5\u8865\u82B1\u7684\uFF0C\u53EA\u662F\u62FF\u4E0D\u4E86\u724C\uFF0C\u672C\u5C40\u7ED3\u675F\u3002\n";

module.exports = helpText;

cc._RFpop();
},{}],"inputNumPanel":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ccb80+jCMxPRrBiBALcFC4k', 'inputNumPanel');
// scripts\hall\inputNumPanel.js

'use strict';

/**
 * @class
 * @classdesc 本类处理数字输入面板
 */
var InputNumPanel = cc.Class({
    name: 'InputNumPanel',

    /**
     * 属性定义
     */
    properties: {
        numsLabel: null, //Label节点，数字面板的最上方显示已经输入的数字的节点
        numBtnNodes: null, //节点数组，数字面板的中间的所有的数字按钮的节点
        hasInputedArr: [], //存放所有已输入的数字  如： [3,8,0,2,0]
        maxLen: 6 },

    /**
     * @constructor
     */
    ctor: function ctor() {
        this.numsLabel = cc.find('Canvas/joinRoomAlert/alertContent/joinRoomBg/roomId/inputBg/roomIdStr').getComponent(cc.Label); //Label节点，数字面板的最上方显示已经输入的数字的节点
        this.numBtnNodes = cc.find('Canvas/joinRoomAlert/alertContent/joinRoomBg/keyboard').children; //节点数组，数字面板的中间的所有的数字按钮的节点

        //清空
        this.reset();
        //添加点击事件的回调
        this._addClick_();
    },

    /**
     * 清除已经输入的全部数字 ，socket中会用
     * @public
     */
    reset: function reset() {
        //删除全部数据
        this.hasInputedArr.length = 0;

        //清除页面的数据
        this.numsLabel.string = "房间号为6位数字";
    },

    /**
     * 添加点击事件
     * @private
     */
    _addClick_: function _addClick_() {
        for (var i = 0; i < this.numBtnNodes.length; i++) {
            this.numBtnNodes[i].on('click', function (event) {
                //为每个数字按钮添加点击事件
                this._pressBtnClick_(event.target.name);
            }, this);
        }
    },

    /**
     * 数字面板上的每个节点点击时都会调用这个函数
     * 函数挂载到页面数字面板上的每个按钮上，不用在其它地方
     * @private
     * @param {Event} event -- 当在编辑器中添加点击事件时，表示点击的事件对象。
     * @param {string} value -- 传入的值只会是 '0' ~ '9', 'del', 'reset'
     */
    _pressBtnClick_: function _pressBtnClick_(value) {
        window.audio.playEffect('waterClick'); //播放点击声音
        switch (value) {
            case "del":
                if (0 !== this.hasInputedArr.length) {
                    //如果还有数据
                    this._del_(); //删除最后一个数据
                }
                break;
            case "reset":
                this.reset(); //清空所有数据
                break;
            default:
                //如果输入了一个数字
                if (this.maxLen > this.hasInputedArr.length) {
                    //如果数字还未存满
                    this._add_(value); //输入一个数字

                    //如果刚好输入6个数字
                    if (this.maxLen == this.hasInputedArr.length) {
                        //发送加入房间的消息
                        if (!clientUser) {
                            getGI('hall').errorReminder('未连接到服务器!');
                            //清空
                            this.reset();
                        } else {
                            getGI('hall').loading('start'); //加载中。。。
                            console.log('点击完成，发送了join消息');
                            clientUser.send({ message: this.hasInputedArr.join(''), type: 'join' });
                        }
                    }
                }
        }
    },

    /**
    * 输入一个数字并显示
    * @private
    * @param {number} value -- 要输入的值
    */
    _add_: function _add_(value) {
        //增加一个
        this.hasInputedArr.push(value);
        //显示到页面
        this.numsLabel.string = this.hasInputedArr.join(' ');
    },

    /**
     * 删除一个数字并显示
     * @private
     */
    _del_: function _del_() {
        //删除一个
        this.hasInputedArr.pop();

        //显示到页面
        if (this.hasInputedArr.length === 0) {
            this.numsLabel.string = '房间号为6位数字';
        } else {
            this.numsLabel.string = this.hasInputedArr.join(' ');
        }
    }

});

module.exports = InputNumPanel;

cc._RFpop();
},{}],"isOnline_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'fdc79Gh8ZRLnrW3kD1fRhO0', 'isOnline_controller');
// scripts\common\controller\messageController\isOnline_controller.js

'use strict';

/**@class
 * @classdesc 离线或重新上线
 */
var IsOnline_controller = cc.Class({
    name: 'IsOnline_controller',

    /**
     * 离线消息, 别人离线后，自己会收到这条消息，不涉及到数据
     * data: {  e | errMsg: null,
                m | message: '38992', //openId
                s | success: true, t | type: 33, leaveLine
            }
     */
    leaveLine_controller: function leaveLine_controller(data) {

        console.log('执行 leaveLine_controller...');
        console.log(data);

        var openId = data.message;
        var user = room.getUser(openId);
        user.attr('state', 0);
        user.displayHead('leaveLine', 'show'); //显示用户的离线标记
        room.attr('isCanDismiss', false); //一旦有人离线，就不可以解散（点解散按钮），如果正在解散过程中，就默认解散失败

        //离线后，如果正在解散房间的过程中，就取消解散的流程
        if (room.dismissAlert) {
            //重置解散弹出框
            room.dismissAlert.reset();
            //隐藏解散弹出框
            room.dismissAlert.dismissShow('hide');
        }

        room && room.displayRoomInfo && room.displayRoomInfo('offLine', user.attr('nickName') + ' 离线了...');

        clientUser.sleep(); //自己不能有任何操作
        socket.finishMsg('leaveLine'); //标记本条消息处理完成
    },

    /**
     * 重连消息，当某个人重新连接上网络后，会发 reConnect 消息，然后服务器会转发给其它人(包括自己)，不涉及到数据
     * data: {  e | errMsg: null,
                m | message: '2899222',
                s | success: true, t | type: 34, reConnect
            }
     */
    reConnect_controller: function reConnect_controller(data) {
        console.log('执行 reConnect_controller...');
        console.log(data);
        var openId = data.message; //重新连接上的人的openId
        var user = room.getUser(openId);
        user.attr('state', 1);

        user.displayHead('leaveLine', 'hide'); //隐藏用户的离线标记

        var isAllOnline = true;
        //如果所有人都在线了，才可以操作
        room.forEach(function (user) {
            if (user && user.attr('state') === 0) {
                //如果还有人离线
                isAllOnline = false;
                return 'break';
            }
        });
        room.attr('isCanDismiss', isAllOnline); //只要还有人离线，就不可以解散（点解散按钮）

        if (user !== clientUser) {
            //自己不显示提示
            room && room.displayRoomInfo && room.displayRoomInfo('onLine', user.attr('nickName') + ' 上线了...');
        }

        isAllOnline && clientUser && clientUser.wake(); //自己不能有任何操作
        socket.finishMsg('reConnect'); //标记本条消息处理完成
    },

    /**
     * 退出房间消息
     * data: {  e | errMsg: null,
                m | message: 10203 ,
                s | success: true, t | type: 36, exitRoom
            }
    */
    exitRoom_controller: function exitRoom_controller(data) {
        console.log('客户端--执行 exitRoom_controller...');

        var userId = data.message;

        if (!room || room.msgIsMeSend(userId)) {
            socket.finishMsg('exitRoom'); //标记本条消息处理完成
            return;
        }

        var user = room.getUser(userId);

        //如果要退出的是庄家， 其它人都被踢出来了
        if (user.attr('zhuang')) {
            cc.director.loadScene('hall', function () {
                window.reset();
                //显示用户的信息
                getGI('hall').display({
                    nickName: clientUser.attr('nickName'), //昵称
                    userId: clientUser.attr('userId'), //用户Id
                    headIcon: clientUser.attr('faceIcon'), //头像图标
                    roomCardNum: clientUser.attr('roomCards') });

                socket.finishMsg('exitRoom'); //标记本条消息处理完成
            });
            //不是庄家退出时，其它人只需将退出的人的相应信息删除就好了
        } else {
            this._resetUserHead_(user); //恢复用户的头像部分为默认
            room.removeUser(user); //从房间中删除用户

            socket.finishMsg('exitRoom'); //标记本条消息处理完成
        }
    },

    /**
     * 恢复用户的头像部分为默认
     * @private
     * @param {object} user --  用户对象
     */
    _resetUserHead_: function _resetUserHead_(user) {
        user.displayHead({
            zhuang: 'hide', //显示 庄家, 注意：传入 'show'表示显示，'hide'表示隐藏
            faceIcon: 'man0', //显示头像  "5350b36d56b6e054.jpg" 显示头像(使用默认的) man0, man1, woman0, woman1
            nickName: 'hide', //显示昵称
            score: 'hide', //分数是0 , 注意：传入 'show'表示显示，'hide'表示隐藏, 传入数字表示更改并显示
            ok: 'hide', //不显示ok
            leaveLine: 'hide', //隐藏离线
            isHost: 'hide' });
    }

});

module.exports = IsOnline_controller;

cc._RFpop();
},{}],"isOnline_serverController":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ad899l2NcJKFJa6ZYtfU84w', 'isOnline_serverController');
// scripts\server\serverController\isOnline_serverController.js

'use strict';

/**@class
 * @classdesc 离线或重新上线
 */
var IsOnline_serverController = cc.Class({
    name: 'IsOnline_serverController',

    /**
     * 离线消息, 别人离线后，自己会收到这条消息，不涉及到数据
     * data: {  e | errMsg: null,
                m | message: '38992', //openId
                s | success: true, t | type: 33, leaveLine
            }
     */
    leaveLine_controller: function leaveLine_controller(data) {},

    /**
     * 重连消息，当某个人重新连接上网络后，会发 reConnect 消息，然后服务器会转发给其它人(包括自己)，不涉及到数据
     * data: {  e | errMsg: null,
                m | message: '2899222',
                s | success: true, t | type: 34, reConnect
            }
     */
    reConnect_controller: function reConnect_controller(data) {},

    /**
     * 退出房间消息
     * data: { openId: 389238,
               message: '' ,
               type: exitRoom
            }
    */
    exitRoom_controller: function exitRoom_controller(data) {
        console.log('服务器--执行 exitRoom_controller...');

        var userId = data.openId;
        var users = window.serverData.roomUsers;

        for (var i = 0; i < users.length; i++) {
            if (users[i].userId === userId) {
                localServer.send({ type: 'exitRoom', message: userId });
                users[i] = null;
            }
        }
    }

});

module.exports = IsOnline_serverController;

cc._RFpop();
},{}],"join_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c79a5zZg9FCqJuZzBmv0qB4', 'join_controller');
// scripts\common\controller\messageController\join_controller.js

'use strict';

var msgComm = require('msgComm');

/**@class
 * @classdesc 一局或八局结束
 */
var Join_controller = cc.Class({
    name: 'Join_controller',

    /**
     * 创建房间
     *
     * data: {  e | errMsg: null,
                m | message: {
                        roomId: 8002//房间号,
                        creator: 38929, //房主
                        totalJuNum: , //没用
                        totalQuanNum: 1, //总局数, 8局--1 16局--2， 分数打法：100分--3 200分--4
                        opt: [1,2], //opt[0]: 2分--1 4分--2 8分--3
                                    //opt[1]: 单游不能平到--1 双游不能平到--2
                        meOrder: 1,
                        roomUsers: [ //先前已经加入房间的用户(包括自己)
                            {cards: 1000, faceIcon:"5350b36d56b6e054.jpg",nickName:"玩家10003",openId:"10003",userId:10003},//先前加入的人
                            {cards: 1000, faceIcon:"5350b36d56b6e054.jpg",nickName:"玩家10003",openId:"10003",userId:10004}, //自己
                        ]
                    },
                s | success: true, t | type: 5, create
            }
     */
    create_controller: function create_controller(data) {
        console.log('客户端--执行 create_controller...');

        this.join_controller(data); //创建房间和加入房间要执行的逻辑一样
    },

    /**
     * 加入房间
     * data: {  e | errMsg: null,
                m | message: {
                        roomId: 8002//房间号,
                        creator: 38929, //房主
                        totalJuNum: , //没用
                        totalQuanNum: 1, //总局数, 8局--1 16局--2， 分数打法：100分--3 200分--4
                        opt: [1,2], //opt[0]: 2分--1 4分--2 8分--3
                                    //opt[1]: 单游不能平到--1 双游不能平到--2
                        meOrder: 1,
                        roomUsers: [ //先前已经加入房间的用户(包括自己)
                            {cards: 1000, faceIcon:"5350b36d56b6e054.jpg",nickName:"玩家10003",openId:"10003",userId:10003},//先前加入的人
                            {cards: 1000, faceIcon:"5350b36d56b6e054.jpg",nickName:"玩家10003",openId:"10003",userId:10004}, //自己
                        ]
                    },
                s | success: true, t | type: 5, join
            }
        }
     */
    join_controller: function join_controller(data) {
        console.log('客户端--执行 join_controller...');
        var message = data.message; //收到的数据
        var meUserId = message.p; //客户的userId
        var players = message.players; //四个用户的信息的数组

        //四个用户按加入的顺序由小到大排序
        players.sort(function (a, b) {
            return a.order - b.order;
        });

        var meIndex = msgComm.getMeInfo(players, meUserId); //客户在players数组中的位置

        message.meIndex = meIndex; //将自己的索引存入
        message.room.activeIndex = message.room.activer - 1;

        msgComm.initRoom(message, function () {
            socket.finishMsg('join'); //标记本条消息处理完成
        });
    }

});

module.exports = Join_controller;

cc._RFpop();
},{"msgComm":"msgComm"}],"join_serverController":[function(require,module,exports){
"use strict";
cc._RFpush(module, '65c78OucxlPkIdBZnFv2Y+3', 'join_serverController');
// scripts\server\serverController\join_serverController.js

'use strict';

var serverMsgData = require('serverMsgData');
/**@class
 * @classdesc 一局或八局结束
 */
var Join_serverController = cc.Class({
    name: 'Join_serverController',
    ctor: function ctor() {
        this.joinNum = 0;
        this.createMsg;
    },

    /**
     * 创建房间
     *
     * data: {  openId: 100004,
     *          //ju：8局--1 16局--2， 分数打法：100分--3 200分--4
     *          //opt[0]: 2分--1 4分--2 8分--3
     *          //opt[1]: 单游不能平到--1 双游不能平到--2
                message: {ju: 1, opt: [1, 2]},
                 type: create
            }
     */
    create_controller: function create_controller(data) {
        console.log('服务器--执行 create_controller...', data);
        var message = data.message;
        this.createMsg = message;

        var roomData = {
            roomId: 8002, //房间号,
            totalJuNum: message.ju, //总局数,
            opt: message.opt, //
            creator: window.testData.test_users[0].userId, //房主
            meOrder: 1, //标记自己是哪个 注意取值为1,2,3,4
            roomUsers: [//先前已经加入房间的用户(包括自己)
            window.testData.test_users[0]]
        };
        window.serverData.roomUsers = [window.testData.test_users[0]];

        localServer.send({ type: 'create', message: roomData });
        this.joinNum = 1;

        this.addThreeUserAuto(); //自动加入另外三个人

        // this.exitOneUserAuto(); //自动退出一个人
        // this.joinOneUserAuto(); //自动加入一个人

        // this.join_controller(data); //创建房间和加入房间要执行的逻辑一样
    },

    /**
     * 加入房间
     * data: {  openId: 38992, //userid
                message: 8003, //房间号
                type: join
            }
     */
    join_controller: function join_controller(data) {
        console.log('服务器--执行 join_controller...', data);
        var userId = data.openId;

        var roomData = {
            roomId: 8002, //房间号,
            totalJuNum: this.createMsg.ju, //总局数,
            opt: this.createMsg.opt, //
            creator: window.testData.test_users[0].userId, //房主
            meOrder: 1, //标记自己是哪个 注意取值为1,2,3,4
            roomUsers: [//先前已经加入房间的用户(包括自己)
            ]
        };

        var localUsers = window.testData.test_users;
        var user = null;
        for (var i = 0; i < localUsers.length; i++) {
            if (localUsers[i].userId === userId) {
                user = localUsers[i];
                break;
            }
        }

        var users = window.serverData.roomUsers;
        var isPush = false;
        var userNum = 0;
        for (var i = 0; i < users.length; i++) {
            if (users[i] === null) {
                users[i] = user;
                userNum = i + 1;
                isPush = true;
                break;
            }
        }
        if (!isPush) {
            users.push(user);
            userNum = users.length;
        }

        roomData.roomUsers = window.serverData.roomUsers;

        var usersNum = 0;
        for (var i = 0; i < roomData.roomUsers.length; i++) {
            if (roomData.roomUsers[i]) {
                usersNum++;
            }
        }
        if (usersNum === 4) {
            var self = this;
            setTimeout(function () {
                serverMsgData.sendStartMsg();
            }, 1000);
        }

        roomData.meOrder = userNum;
        localServer.send({ type: 'join', message: roomData });
    },

    /**
     * 自动发送一条退出房间的消息
     */
    exitOneUserAuto: function exitOneUserAuto() {
        setTimeout(function () {
            //发送退出房间的消息
            window.socket.send({ type: 'exitRoom', openId: window.testData.test_users[2].userId });
        }, 8000);
    },

    /**
     * 自动发送一条加入房间的消息
     */
    joinOneUserAuto: function joinOneUserAuto() {
        setTimeout(function () {
            //发送加入房间的消息
            window.socket.send({ message: 8002, type: 'join', openId: window.testData.test_users[2].userId });
        }, 14000);
    },

    /**
     * 自动发送另外三个人的加入房间的消息
     */
    addThreeUserAuto: function addThreeUserAuto() {
        var self = this;
        this.timer = setInterval(function () {
            if (self.joinNum > 3) {
                self.joinNum = 0;
                clearInterval(self.timer);
                return;
            } else {
                window.socket.send({ message: 8002, type: 'join', openId: window.testData.test_users[self.joinNum].userId });
            }
            self.joinNum++;
        }, 1000);
    }

});

module.exports = Join_serverController;

cc._RFpop();
},{"serverMsgData":"serverMsgData"}],"juEnd_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c5a77zT0QVAfLB5YkPE6cnb', 'juEnd_controller');
// scripts\common\controller\messageController\juEnd_controller.js

'use strict';

/**@class
 * @classdesc 一局或八局结束
 */
var JuEnd_controller = cc.Class({
    name: 'JuEnd_controller',

    /**
     * 每一局结束，结束后不会再有roomscore消息，因为roomScore消息和juScore消息完全一样
     * data: {  e | errMsg: null, s | success: true, t | type: 20, juScore
                m | message: [playInfo1, playInfo2, playInfo3, playInfo4], //四个用户的详细信息
            }
      [playInfo1, playInfo2, playInfo3, playInfo4]
      playInfo1 = {
            outAllCards: ['w3', 't6', 'b', 't3'], //所有出的牌
            outCards: ['w3', 't6', 'b', 't3'] , //剩余出的牌(被别人碰了后，自己出的牌会少一张)
            handCards: ['w3', 't6', 'w6', 'o8'], //手牌
            optMsgs: [...], //操作类型,吃碰杠等的数据
            huaCards: ['i1', 'i3'], //补了的花
            order: 1, //加入房间的顺序，取值：1,2,3,4
            userInfo: { //用户信息
                ready: true, //是否准备好了
                cards: 1000, sex: 1, ip: '192.168.0.196', faceIcon: "5350b36d56b6e054.jpg", isVip: true,
                nickName: "玩家10003", openId: "10003", userId: 10004, state: 0, //0-离线 1-在线
            },
              huInfo:{
                zhuangCount: 3, //庄家总次数  paoCount：2， //点炮总次数
                huCount: 1, //胡牌的总次数  lZCount: 2, //连庄的总次数
            }
              currentScore: 39, //上局得分
            roomScore: 99, //自己的总分
            napai: '', //可能分别为 'w5' null, 但是每个人都能看到 'w5'
        }
          msg =  {  e : null, s : true, t: 7,
            m : {t:7, m: 'w3', 'p': '10114'} ,//出牌人的信息
        };
          playInfo1.optMsgs =
             [
                {p: 289383,  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
                {p: 289383,  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] },//明杠
                {p: 289383,  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] },//暗杠
                {p: 289383,  t: 'buGang', c: 'w3', from: 3892938, m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
                {p: 289383,  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] },//吃
                  {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
                {p: 289383,  t: 'sanJingDao', c: 'w2', from: 3892938, m:[] }, //三金倒
                {p: 289383,  t: 'youJing', c: 'w2', from: 3892938, m:[] }, //游金
                {p: 289383,  t: 'shuangYou', c: 'w2', from: 3892938, m:[] }, //双游
                {p: 289383,  t: 'sanYou', c: 'w2', from: 3892938, m:[] }, //三游
                {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
                {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
            ]
       */
    juScore_controller: function juScore_controller(data) {
        console.log('客户端-- 执行 juScore_controller...');
        // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息
        console.log(data);

        room.attr('period', 'endJu');

        room.singleResult.refreshMahResult(data.message.players); //更改结算数据
        room.singleResult.singelResultShow('show'); //显示结算界面

        var roomMsg = data.message.room;

        room.displayPointer(-1); //停止中间的计时器，并且不指向任何一个人
        room['currentJuNum'] = roomMsg.useJuNum; //当前局数
        room['totalJuNum'] = roomMsg.totalJuNum; //总局数
        room['totalQuan'] = roomMsg.totalQuanNum; //总圈数
        room['currentQuan'] = roomMsg.useQuanNum; //当前圈数
        room['nextZhuang'] = parseInt(roomMsg.zhuangId); //下一局的庄家

        socket.finishMsg('juScore'); //标记本条消息处理完成
    }

});

module.exports = JuEnd_controller;

cc._RFpop();
},{}],"mahjongMngBase":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0b11201TahDV7pX7OOztS28', 'mahjongMngBase');
// scripts\common\controller\mahjongMngBase.js

'use strict';

var Combination = require('combination'); //状态类
var CardsMng = require('cardsMng'); //管理牌的类


/**@class
 * @classdesc 管理明牌的类, 本类只作为基类使用，不创建对象
 */
var MahjongMngBase = cc.Class({
    name: 'MahjongMngBase',

    /**
      * @constructor
      * @param {Object} config -- {
      *                              userSelf //用户自己
      *                           }
      */
    ctor: function ctor() {
        var config = arguments[0];
        this.userSelf = config.userSelf; //用户自己

        this.mahView = this._getMahView_(); //获取页面刷新管理对像
        this.mahModel = new CardsMng(); //牌的管理对像
        this.animate = this._getAnimate_(); //获取页面动画管理对像
    },

    /**
     * 当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        console.log('mahjongMng.js重置了。');
        this.mahView.reset(); //页面刷新管理对像
        this.mahModel.reset(); //牌的管理对像
        // this.animate.reset(); //页面动画管理对像
    },

    /**
    * 添加一副牌
    * @public
    * @param {Object} config -- {handCards: 17 |  [ 'e', 'w2', 't8', 'n'] , outAllCards: null, outCards: null, mingCards: null, huaCards: users[i].hua,}
    *
    */
    setCards: function setCards() {
        //添加牌的数据
        this.mahModel.setCards(arguments[0].handCards); //手牌
        this.mahModel.operateAttr('cardsArr', arguments[0].mingCards); //操作牌
        this.mahModel.outArr('cardsArrOgn', arguments[0].outAllCards); //全部出的牌
        this.mahModel.outArr('cardsArrN', arguments[0].outCards); //出的牌

        this.userSelf.attr('huaCards', arguments[0].huaCards); //记录要补的花

        //如果是客户
        if (this.userSelf.attr('isClient')) {
            room['nextZhuang'] && room.setZhuang(room['nextZhuang']); //更改庄家
        }
    },

    showCards: function showCards(period) {
        switch (period) {
            case 'beiMian':
                //刷新手中的牌的显示
                var cards = this.mahModel.getHandCard();
                console.log(this.userSelf.attr('nickName') + ' 的用户的牌是： ' + cards);

                //显示倒牌的背面
                this.mahView.convertHM('handd');
                var cardNum = cards;
                if (typeof cards !== 'number') {
                    cardNum = cards.length;
                }

                if (cardNum && cardNum % 3 === 2) {
                    //如果该出牌，就显示出牌
                    this.mahView.displayHand(cardNum, 'wantOutCard'); // => number | Array
                } else {
                    this.mahView.displayHand(cardNum); // => number | Array
                }

                break;
            case 'mix':
                this.mahView.convertHM('hand');
                var cards = this.userSelf.attr('originalCards');
                if (cards && (cards.length ? cards.length % 3 === 2 : cards % 3 === 2)) {
                    //如果该出牌，就显示出牌
                    this.mahView.displayHand(cards, 'wantOutCard'); // => number | Array //显示乱序的牌
                } else {
                    this.mahView.displayHand(cards); // => number | Array //显示乱序的牌
                }
                break;
            case 'order':
                //刷新手中的牌的显示
                var cards = this.mahModel.getHandCard();

                if (cards && (cards.length ? cards.length % 3 === 2 : cards % 3 === 2)) {
                    //如果该出牌，就显示出牌
                    this.mahView.displayHand(cards, 'wantOutCard'); // => number | Array //显示排序的牌
                } else {
                    this.mahView.displayHand(cards); // => number | Array //显示排序的牌
                }

                break;
            case 'allOut':
                //显示所有出的牌
                var outArr = this.mahModel.outArr('cardsArrN');
                this.mahView.operateAllOutCard(outArr);
                break;
            case 'allMing':
                //显示所有明牌
                var mingArr = this.mahModel.operateAttr('cardsArr');
                this.mahView.displayAllMing(mingArr);
                break;
            case 'buHua':
                this.startBuHua(); //进行补花
                break;
            case 'isCanOutCard':
                //如果是客户
                if (this.userSelf.attr('isClient')) {
                    //如果是庄家，就可以出牌
                    this.mahView.outCardEventAttr('isCanPlay', this.userSelf.attr('zhuang'));
                }
                break;
            case 'showPanel':
                var optArr = this.userSelf.attr('operateArr');
                if (optArr && optArr.length > 0) {
                    //如果有操作
                    this.showPanel(optArr);
                }
                break;
        }
    },

    /**
     * 开始时的补花
     * @param {boolean} isInCard -- true表示将补花拿到的牌放到拿牌的位置， false表示放到手牌中
     */
    startBuHua: function startBuHua() {
        var obj = this.userSelf.attr({
            userId: undefined, haveCard: undefined, huaCards: undefined, processPeriod: undefined
        });
        var huaCards = obj.huaCards;
        if (!huaCards) {
            return;
        }
        var index = 0,
            self = this,
            isHaveCard = obj.haveCard,
            //手中是否有牌
        userId = obj.userId,
            //用户id
        processPeriod = obj.processPeriod;
        // var goldCard = room.goldCard; //金牌

        // this.timer = setInterval(function(){
        for (var index = 0; index < huaCards.length; index++) {
            var hua = huaCards[index].h; //要补的花
            var cardName = huaCards[index].c; //补到的牌
            var operate = self.mahModel.operate({ p: userId, t: 'buHua', c: hua, from: userId, m: [hua] }); //修改补花的数据,减一张花牌

            if (isHaveCard) {
                //如果有牌
                if (processPeriod === 'inCard') {
                    //如果是拿了牌(包括开始发牌时的庄家)
                    var cards = self.mahModel.getHandCard();
                    self.mahView.displayHand(cards); //显示排序的牌
                    //添加补到的牌
                    self.mahModel.inCard(cardName);
                    self.mahView.inCard(cardName);
                } else {
                    //添加补到的牌
                    self.mahModel.inCard(cardName);
                    var cards = self.mahModel.getHandCard();
                    self.mahView.displayHand(cards); //显示排序的牌
                }
            } else {
                //如果没有牌，就只播放声音就可以了
                //添加补到的牌
                self.mahModel.inCard(cardName);
            }

            self.mahView.displayOneMing(operate); //显示补花
            // index++;
            // if(index >= huaCards.length){
            //     clearInterval(self.timer);
            // }
        }
        // }, room.attr('buHuaOnceT')* 1000 ); //默认为 0.8s
        this.userSelf.attr('huaCards', null);
    },

    /**
     * 拿牌
     * @public
     * @param {string} cardName -- 要拿的牌 可以是 'w3' 或 ''
     * @param {Array} optArr -- 自己能对这张牌进行的操作
    */
    inCard: function inCard(cardName, optArr) {

        console.log('执行了mahjongMngBase.inCard' + cardName);

        //更改数据
        this.mahModel.inCard(cardName);

        //刷新手中的牌的显示
        this.mahView.inCard(cardName);

        if (this.userSelf.attr('isClient')) {
            if (optArr && optArr.length > 0) {
                //如果有操作
                this.userSelf.attr('operateArr', optArr);
                this.showPanel(TOOL.converOperate(optArr));
            } else {
                this.mahView.outCardEventAttr('isCanPlay', true); //启用出牌
            }
        }
    },

    /**
     * 出牌
     * @public
     * @param {string} cardName -- 要出的牌
     * @param {string} cardFrom  -- 出牌人的openId
    */
    outCard: function outCard(cardName, finishedFunc) {
        //更改数据
        this.mahModel.outCard(cardName);

        //刷新手中的牌的显示
        var cards = this.mahModel.getHandCard();
        this.mahView.displayHand(cards);

        //设置到页面，但不显示
        var nextNode = this.mahView.operateOutCard('forward', cardName);

        nextNode.active = true;

        room.playOutSkipAni(nextNode);

        //出牌动画，完成后再显示，并显示 跳动的小红点
        // this.animate.outCardAni( nextNode, cardName, function(outPosition){
        //     finishedFunc && finishedFunc();
        //     room.playOutSkipAni(outPosition); //跳动的小红点
        // } );
    },

    /**
     * 专门处理重构场景时的出牌消息
     * @public
     * @param {string} cardName -- 要出的牌
     * @param {string} cardFrom  -- 出牌人的openId
     */
    rebuildOutCard: function rebuildOutCard(cardName, cardFrom) {

        var currentNode = this.mahView.getCurrentNode(); //当前正在显示的出牌的节
        //有可以重构时，还没有一个人出牌，所以 currentNode可以为null
        if (currentNode) {
            //出牌动画，完成后再显示，并显示 跳动的小红点
            this.animate.outCardAni(currentNode, cardName, function (outPosition) {
                room.playOutSkipAni(outPosition); //跳动的小红点
            });
        }

        if (room.msgIsMeSend(cardFrom)) {
            //如果是自己发的出牌消息
            this.outCardPCheck(cardName, cardFrom);
        }
    },

    /**
     * 获取页面刷新管理对像
     * @private
     */
    _getMahView_: function _getMahView_() {

        var combination = new Combination();
        combination.combination([

        //碰、杠
        { name: 'mingCardMng', combineType: 'method', type: 'class', config: this.userSelf.attr('userNode') },

        //出牌
        { name: 'outCardMng', combineType: 'method', type: 'class', config: this.userSelf.attr('userNode') },

        //手中的牌的显示
        { name: 'handCardMng', combineType: 'method', type: 'class', config: this.userSelf }]);

        if (!this.userSelf.attr('isClient')) {
            //如果不是用户自己
            return combination;
        } else {
            return combination.combination([//只有自己才能操作面板
            //选项面板
            { name: 'hand_operatePanel', combineType: 'method', type: 'class', config: this.userSelf.attr('userNode') },

            //其它的显示
            // {name:'hand_otherDisplay', combineType:'method', type:'class', config:{
            //     userSelf: this.userSelf //用户自己
            // }},

            //出牌的点击事件
            { name: 'hand_outCardEvent', combineType: 'method', type: 'class', config: this.userSelf.attr('userNode') }]);
        }
    },

    /**
     * 获取页面动画管理对像
     * @private
     */
    _getAnimate_: function _getAnimate_() {
        var combination = new Combination();
        return combination.combination([

            //选择碰、杠后的动画
            // {name:'animate_operate',  combineType:'method', type:'class', config:{
            //     userSelf: this.userSelf //用户自己
            // }},

            //出牌后的动画
            // {name:'animate_outCard',  combineType:'method', type:'class', config:{
            //     userSelf: this.userSelf //用户自己
            // }},

        ]);
    }

});

module.exports = MahjongMngBase;

cc._RFpop();
},{"cardsMng":"cardsMng","combination":"combination"}],"mahjongMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '4e15bbvWQlDtbZ2s98nsxv6', 'mahjongMng');
// scripts\common\controller\mahjongMng.js

'use strict';

var MahjongMngBase = require('mahjongMngBase'); //状态类

/**@class
 * @classdesc 管理明牌的类
 */
var MahjongMng = cc.Class({
    name: 'MahjongMng',
    extends: MahjongMngBase,
    /**
      * @constructor
      */
    ctor: function ctor() {},

    /**
     * 检查是否有这张牌，如果没有，就返回手中的的某张牌
     */
    checkNum: function checkNum(cardName) {
        return this.mahModel.checkNum(cardName);
    },

    /**
     * 选择某一操作后进行的处理（如，点了碰后）
     * hu, pong, guo, chi, ting, mingGang, anGang, buGang
     * @public
     * @param {Object} operateData -- 操作的数据
     * 示例：operateData = {operateName:'buGang', operateCard: card, index: i} //可能同时有多个补扛
     */
    operateItem: function operateItem(operateData) {
        var operateName = operateData.t; //hu, pong, guo, chi, ting, mingGang, anGang, buGang
        switch (operateName) {
            case 'buHua':
                this._buHua_(operateData);
                break;
            case 'pong':
            case 'chi':
            case 'mingGang':
            case 'anGang':
            case 'buGang':
                this.comm(operateData);
                break;

            case 'youJing':
            case 'shuangYou':
            case 'sanYou':
            case 'sanJingDao':
                console.log('mahjongMng.js- ' + operateName);
                break;

            case 'hu':
                break;
        }
    },

    /**
     * 进行补花
     * @private
     * @param {Object} operateData -- 操作的数据
     * 示例：operateData = {p: who, t: 'buHua', c: 'i1', from: who, m:[]}
     */
    _buHua_: function _buHua_(operateData) {
        //更改数据
        var data = this.mahModel.operate(operateData);
        //显示到页面
        this.mahView.displayOneMing(data);
    },

    comm: function comm(operateData) {
        //更改数据
        this.mahModel.operate(operateData);

        //刷新手中的牌的显示
        var cards = this.mahModel.getHandCard();
        if (operateData.t === 'pong' || operateData.t === 'chi') {
            this.mahView.displayHand(cards, 'wantOutCard');
        } else {
            this.mahView.displayHand(cards);
        }

        // //碰的动画
        // this.animate.processCardAni(operateData.operateName);

        //将碰的牌刷新到页面
        this.mahView.displayOneMing(operateData);

        //如果是客户
        if (this.userSelf.attr('isClient')) {
            this.mahView.outCardEventAttr('isCanPlay', true); //启用出牌
            this.mahView.outCardEventAttr('isCanOperate', true); //启用用出牌的点击事件
        }
    },

    /**
     * 弹出选项面板
     * @public
     * @param {Array} optArr -- 操作的数据
     *  optArr =  [
     *              {p: '289383',  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
                    {p: '289383',  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] }, //明杠
                    {p: '289383',  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] }, //暗杠
                    {p: '289383',  t: 'buGang', c: 'w3', from: 3892938, m:['w3'] }, //补杠, 与碰是分开的
                    {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w3', 'w4'] }, //吃
                    {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] }, //吃
     *           ]
     */
    showPanel: function showPanel(optArr) {
        this.mahView.showPanel(optArr); //显示到页面, 弹出选项面板
        this.mahView.outCardEventAttr('isCanOperate', false); //整个点击事件不可用
        this.userSelf.attr('isCanOperateClick', true); //可以点击
    }

});

module.exports = MahjongMng;

cc._RFpop();
},{"mahjongMngBase":"mahjongMngBase"}],"messageAlert_click":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd4b57gd8fVN+orD7M6kY/QR', 'messageAlert_click');
// scripts\room\view\alert\messageAlert_click.js

'use strict';

/**
 * 信息弹出框上的事件
 */
cc.Class({
    extends: cc.Component,

    onLoad: function onLoad() {
        this._test_msg_();
        // this._test_msgShow_();
    },

    /**
     * 测试信息弹出框
     */
    _test_msg_: function _test_msg_() {
        var MessageAlert_display = require('messageAlert_display');
        this.test_messageAlert = new MessageAlert_display();
        //生成文字
        this.test_messageAlert.generateWord(this.wordClick, this);
        // //生成表情
        this.test_messageAlert.generateFace(this.faceClick, this);
    },

    /**
     * 测试信息弹出框
     */
    _test_msgShow_: function _test_msgShow_() {
        var UserMsgShow = require('userMsgShow');

        for (var i = 0; i < 4; i++) {
            if ('touches' in cc.sys.capabilities) {
                cc.find('Canvas/roomMain/user' + i + '/nickBorder/nickName').on('touchend', function (e) {
                    var userIndex = e.target.parent.parent.name.charAt(4);
                    this.test_userMsg.test_setUser(cc.find('Canvas/roomMain/user' + userIndex));
                }, this);
            } else if ('mouse' in cc.sys.capabilities) {
                console.log('bb');
                cc.find('Canvas/roomMain/user' + i + '/nickBorder/nickName').on('mouseup', function (e) {
                    var userIndex = e.target.parent.parent.name.charAt(4);
                    this.test_userMsg.test_setUser(cc.find('Canvas/roomMain/user' + userIndex));
                }, this);
            }
        }

        this.test_userMsg = new UserMsgShow({ userNode: cc.find('Canvas/roomMain/user0') });
    },

    /**
     * 常用语: 常用语的按钮点击时调用的函数
     * 1.挂载于 最上方的切换表情和常用语的按钮上
     * @public
     */
    wordBtnClick: function wordBtnClick(event) {
        window.audio.playEffect('waterClick'); //播放点击声音

        cc.find('Canvas/msgAlert/alertContent/wordScrollView').active = true;
        cc.find('Canvas/msgAlert/alertContent/faceScrollView').active = false;
    },

    /**
     * 表情： 表情的按钮点击时调用的函数
     * 1.挂载于 最上方的切换表情和常用语的按钮上
     * @public
     */
    faceBtnClick: function faceBtnClick(event) {
        window.audio.playEffect('waterClick'); //播放点击声音

        //显示表情框
        cc.find('Canvas/msgAlert/alertContent/faceScrollView').active = true;
        cc.find('Canvas/msgAlert/alertContent/wordScrollView').active = false;
    },

    /**
     * 常用语: 每一个常用语上面的点击事件调用的函数
     * 1.挂载于 每一句常用语的文字上面
     * @public
     */
    wordClick: function wordClick(e) {
        this.test_messageAlert.messageAlertShow('hide'); //隐藏弹出框
        window.audio.playEffect('waterClick'); //播放点击声音
        var wordNum = e.target.name; // 'c05'
        console.log("发送了文字信息：" + wordNum);

        console.log({ message: wordNum, type: 'word' });
        // this.test_userMsg.say( {type: 'word', value: words[i]} );
        clientUser.send({ message: wordNum, type: 'word' }); //发送文字消息
    },

    /**
     * 表情: 每一个表情上面的点击事件调用的函数
     * 1.挂载于 每一个表情图片上面
     * @public
     */
    faceClick: function faceClick(e) {
        window.audio.playEffect('waterClick'); //播放点击声音

        //e.target 是当前点击的那个节点对象 name='EE3'
        var name = e.target.name;
        console.log("选中的表情的名字是：" + name);

        //隐藏弹出框
        this.test_messageAlert.messageAlertShow('hide');
        console.log({ message: name, type: 'face' });

        // this.test_userMsg.say( {type: 'face', value: name} );

        //发送文字消息
        clientUser.send({ message: name, type: 'face' });
    }

});

cc._RFpop();
},{"messageAlert_display":"messageAlert_display","userMsgShow":"userMsgShow"}],"messageAlert_display":[function(require,module,exports){
"use strict";
cc._RFpush(module, '71fedXzD4JC7qoL9ef86YJO', 'messageAlert_display');
// scripts\room\view\alert\messageAlert_display.js

'use strict';

/** 信息弹出框 */
var MessageAlert_display = cc.Class({
    name: 'MessageAlert_display',

    /**
     * @constructor
     */
    ctor: function ctor() {
        var roomGlobalRes = getGI('roomGlobalRes');
        this.face_Atlas = roomGlobalRes.face_Atlas; //表情的图集

        this.wordPref = roomGlobalRes.wordPref; //预制节点，常用语
        this.facePref = roomGlobalRes.facePref; //单个表情节点


        this.messageAlert = cc.find('Canvas/msgAlert');
        this.words = window.words; //从全局获取所有的文字

        this.wordBox = cc.find('alertContent/wordScrollView/view/content', this.messageAlert);
        this.faceBox = cc.find('alertContent/faceScrollView/view/content', this.messageAlert);
    },

    /**
     * 表情: 将表情图集this.face_Atlas中的所有表情添加到页面
     * @public
     * @param {string} faceName -- 表情名 如：'EE3'
     */
    generateFace: function generateFace(clickCallback, thisObj) {
        var spriteFrameArr = this.face_Atlas.getSpriteFrames(); //图集中的所有图片
        for (var i = 0; i < spriteFrameArr.length; i++) {
            this._addOneFace_(spriteFrameArr[i], clickCallback, thisObj);
        }
    },

    /**
     * 常用语: 将this.words中的常用语添加到页面
     * @public
     */
    generateWord: function generateWord(clickCallback, thisObj) {
        var data = this.words.order; // ['c00', 'c01', 'c02', 'c03', 'c04', 'c05', 'c06', 'c07', 'c08', 'c09']
        for (var i = 0; i < data.length; i++) {
            this._addOneWord_(data[i], i, clickCallback, thisObj);
        }
    },

    /**
     * 信息框的显示和隐藏
     * @public
     * @param {string} isShow -- 'show' 表示显示， 'false' 表示隐藏
     */
    messageAlertShow: function messageAlertShow(isShow) {
        // audio.play('waterClick');
        isShow = isShow === 'show' ? true : false;
        this.messageAlert.active = isShow;
    },

    /**
     * 常用语: 增加一个常用语在列表中
     * @private
     * @param {string} name -- 要显示的文字的编号
     */
    _addOneWord_: function _addOneWord_(wordNum, i, clickCallback, thisObj) {
        // console.log("执行了函数 _addOneWord_, 传入的参数分别是：", message, i);
        var node = cc.instantiate(this.wordPref); //使用预制节点创建一个新的节点
        node.getComponent(cc.Label).string = this.words.data[wordNum]; //更新信息
        node.name = wordNum; // 'c02'
        node.on('click', clickCallback, thisObj); //注册点击事件
        node.parent = this.wordBox; //添加到页面
    },

    /**
     * 表情: 将单个spriteFrame添加到页面
     * @private
     * @param {cc.SpriteFrame} spriteFrame -- 表情的图片
     */
    _addOneFace_: function _addOneFace_(spriteFrame, clickCallback, thisObj) {
        // console.log("执行了函数 addOneFace, 传入的参数分别是：", spriteFrame);
        var node = cc.instantiate(this.facePref); //使用预制节点创建一个新的节点
        node.getComponent(cc.Sprite).spriteFrame = spriteFrame; //更改表情
        node.name = spriteFrame.name;
        node.on('click', clickCallback, thisObj); //注册点击事件
        node.parent = this.faceBox;
    }

});

module.exports = MessageAlert_display;

cc._RFpop();
},{}],"mingCardMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '01fd1MMxEZNjqExpZCmnkcW', 'mingCardMng');
// scripts\room\view\userLayer\display\mingCardMng.js

'use strict';

/**@class
 * @classdesc 管理明牌的类
 */
var MingCardMng = cc.Class({
    name: 'MingCardMng',

    /**
      * @constructor
      * @param {Object} userNode
      */
    ctor: function ctor() {
        var userNode = arguments[0];

        this.buHuaNode = userNode.getChildByName('buHua'); //补花
        this.showNode = userNode.getChildByName('ming'); //碰、扛的牌的父结点
        this.mj_Atlas = getGI('roomGlobalRes').mj_Atlas; //麻将牌的图集

        this.currentShowNodeArr = null; //用于存放碰、扛的牌数组, 即那四张牌对应的节点
        this.showPointer = -1; //一个指针，用于指向当前已经显示到第几个了，0是第一个， -1表示现在还没指向任何一个(即什么也没显示)
        this.hideAllMingCard(); //隐藏所有碰、扛的牌
    },

    /**
     * 当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        console.log('mingCardMng.js重置了。');
        this.showPointer = -1; //一个指针，用于指向当前已经显示到第几个了，0是第一个， -1表示现在还没指向任何一个(即什么也没显示)
        this.hideAllMingCard(); //隐藏所有碰、扛的牌，所有的花牌隐藏
    },

    /**
     * 隐藏所有碰、扛的牌
     * @public
    */
    hideAllMingCard: function hideAllMingCard() {
        var node = null;
        for (var i = 0; i < 5; i++) {
            node = this.showNode.getChildByName('operate' + i);
            node.active = false; //隐藏节点
            node.getChildByName('mj3').active = false; //第四张牌隐藏
        }

        //所有的花牌隐藏
        var arr = this.buHuaNode.children;
        for (var i = 0; i < 8; i++) {
            arr[i].active = false;
        }
    },

    /**
     * 一次性显示所有的操作的牌
     * @public
     * @param {Array} arr -- [
     *      {p: '289383',  t: 'pong', c: 'w3', from: '3892938', m:['w3', 'w3'] }, //碰
            {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3', 'w3'] },//明杠
            {p: '289383',  t: 'anGang', c: 'w3', from: '3892938', m:['w3', 'w3', 'w3', 'w3'] },//暗杠
            {p: '289383',  t: 'buGang', c: 'w3', from: '3892938', m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
            {p: '289383',  t: 'chi', c: 'w2', from: '3892938', m:['w1', 'w3', 'w2'] },//吃
            {p: '3889289', t: 'buHua', c:'', from: 'meOpenId', m:['i1', 'h3', 'i2']} //补花合在 specialGang中了
     *  ]
     */
    displayAllMing: function displayAllMing(arr) {
        for (var i = 0; i < arr.length; i++) {
            this.displayOneMing(arr[i]);
        }
    },

    /**
     * 显示一次碰、扛牌
     *
     * @public
     * @param {Object} operateData -- 要显示的操作的信息对象
     *      {p: '289383',  t: 'pong', c: 'w3', from: '3892938', m:['w3', 'w3'] }, //碰
            {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3', 'w3'] },//明杠
            {p: '289383',  t: 'anGang', c: 'w3', from: '3892938', m:['w3', 'w3', 'w3', 'w3'] },//暗杠
            {p: '289383',  t: 'buGang', c: 'w3', from: '3892938', m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
            {p: '289383',  t: 'chi', c: 'w2', from: '3892938', m:['w1', 'w3'] },//吃
            {p: '3889289', t: 'buHua', c:'i2', from: 'meOpenId', m:['i1', 'h3', 'i2']} //补花合在 specialGang中了
     */
    displayOneMing: function displayOneMing(operateData) {

        if (operateData.t === 'buGang') {
            //补扛时，传入自己的指针
            this.currentShowNodeArr = this.showNode.getChildByName('operate' + operateData.index).children; //将要显示的四张牌对应的节点数组
        } else if (operateData.t === 'buHua') {
            this.currentShowNodeArr = this.buHuaNode.children;
        } else {
            this.showPointer++; //指向要显示的位置
            this.currentShowNodeArr = this.showNode.getChildByName('operate' + this.showPointer).children; //将要显示的四张牌对应的节点数组
        }

        this.currentShowNodeArr.sort(function (a, b) {
            //排序，按名字的升序排
            return a.name.slice(-1) - b.name.slice(-1);
        });

        var cardName = operateData.c;
        var arr = operateData.m; //要显示的数组
        var start = 0; //从第几张牌开始显示
        var lastIsShow = ''; //第四张牌是否显示, ''表示根本没有第四张牌

        switch (operateData.t) {
            case 'chi':
                //吃
                lastIsShow = false;
                for (var j = start; j < arr.length; j++) {
                    //设置为指定的牌
                    this._setMj_(this.currentShowNodeArr[j], arr[j]); //更改牌
                    if (arr[j] === cardName) {
                        //如果是吃的那张牌，就要显示遮罩
                        this.currentShowNodeArr[j].getChildByName('mask').active = true; //显示遮罩
                    } else {
                        this.currentShowNodeArr[j].getChildByName('mask').active = false; //隐藏遮罩
                    }
                }
                if (lastIsShow !== '') {
                    //如果有第四张牌
                    this.currentShowNodeArr[3].active = lastIsShow; //设置第四张牌是否显示
                    this.currentShowNodeArr[3].parent.active = true; //显示整组牌
                }
                return; //注意，这里是reutrn
            case 'pong':
                //碰
                arr.push(cardName);
                lastIsShow = false;
                break;
            case 'mingGang':
                //明杠
                arr.push(cardName);
                lastIsShow = true;
                break;
            case 'buGang':
                //补杠
                start = 3;
                arr = ['mjB', 'mjB', 'mjB', 'mjB'];
                lastIsShow = true;
                break;
            case 'anGang':
                //暗杠
                if (operateData.p === clientUser.attr('userId')) {
                    arr = ['mjB', 'mjB', 'mjB', cardName];
                } else {
                    arr = ['mjB', 'mjB', 'mjB', 'mjB'];
                }
                lastIsShow = true;
                break;
            case 'buHua':
                for (var j = start; j < arr.length; j++) {
                    //设置为指定的牌
                    this.currentShowNodeArr[j].getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame('y_' + arr[j]);
                    this.currentShowNodeArr[j].active = true; //显示
                }
                return; //注意，这里是reutrn
        }

        for (var j = start; j < arr.length; j++) {
            //设置为指定的牌
            this._setMj_(this.currentShowNodeArr[j], arr[j]); //更改牌
            this.currentShowNodeArr[j].getChildByName('mask').active = false; //隐藏遮罩
        }

        if (lastIsShow !== '') {
            //如果有第四张牌
            this.currentShowNodeArr[3].active = lastIsShow; //设置第四张牌是否显示
            this.currentShowNodeArr[3].parent.active = true; //显示整组牌
        }
    },

    /**
     * 更改麻将牌
     * @private
     * @param {cc.Node} mjNode -- 麻将节点
     * @param {string} cardName -- 牌的名字
     */
    _setMj_: function _setMj_(mjNode, cardName) {
        var spriteFrame = mjNode.getComponent(cc.Sprite).spriteFrame.name;
        switch (cardName) {
            case 'mjB':
                //显示为背面
                if (spriteFrame.search('mingb') === -1) {
                    //如果是正面
                    spriteFrame = spriteFrame.replace('ming', 'mingb');
                    mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                }
                mjNode.getChildByName('hua').active = false;
                break;
            case 'mjF':
                //显示为正面
                if (spriteFrame.search('mingb') !== -1) {
                    //如果是背面
                    spriteFrame = spriteFrame.replace('mingb', 'ming');
                    mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                }
                mjNode.getChildByName('hua').active = true;
                break;
            default:
                //显示为正面，并更改牌的花色
                if (spriteFrame.search('mingb') !== -1) {
                    //如果是背面
                    spriteFrame = spriteFrame.replace('mingb', 'ming');
                    mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(spriteFrame);
                }
                mjNode.getChildByName('hua').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardName);
                mjNode.getChildByName('hua').active = true;
        }
    }

});

module.exports = MingCardMng;

cc._RFpop();
},{}],"msgCache":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c6c9crJfjxEqolCL67FNNIY', 'msgCache');
// scripts\common\msgMng\msgCache.js

'use strict';

/**
 * @class
 * @classdesc 用于缓存来不及处理的消息
 */
var MsgCache = cc.Class({
    name: 'MsgCache',
    /**
     * @constructor
     */
    ctor: function ctor() {
        this.controllerMap = arguments[0];
        this.msgArr = [];
        this.timer = null;
        this.isProcessing = false; //标记是否正在处理中
    },

    reset: function reset() {
        this.msgArr.length === 0;
        clearInterval(this.timer);
        this.timer = null;
        this.isProcessing = false; //标记是否正在处理中
    },

    pushMsg: function pushMsg(msg) {
        //如果已经处理完成
        if (!this.isProcessing) {
            //如果设置了条件
            if (msgCondition[msg.type]) {
                //注意，msgCondition是一个全局变量
                var len = msgCondition[msg.type].length;
                var isNeedCache = true;
                for (var i = 0; i < len; i++) {
                    if (!window.CONDITIONS[msgCondition[msg.type][i]](msg)) {
                        //如果有一个条件不满足
                        isNeedCache = false;
                        break;
                    }
                }
                if (isNeedCache) {
                    //如果所有条件都满足，就直接执行
                    this.isProcessing = true; //标记正在处理中
                    console.log('1开始处理 ' + msg.type + ' 消息！');
                    this.controllerMap.accessController(msg); //调用各自控制器
                    return;
                } else {
                    //本条消息的条件不满足，就需要缓存
                    this.msgArr.push(msg);
                    if (this.timer === null) {
                        //说明还没开启定时器
                        this._registerTimer_();
                    }
                }
            } else {
                //如果没有条件就直接处理
                this.isProcessing = true; //标记正在处理中
                console.log('2开始处理 ' + msg.type + ' 消息！');
                this.controllerMap.accessController(msg); //调用各自控制器
                return;
            }
        } else {
            //如果上条消息没有处理完成，就需要缓存
            this.msgArr.push(msg);

            if (this.timer === null) {
                //说明还没开启定时器
                this._registerTimer_();
            }
        }
    },

    /**
     * 注册定时器
     * @private
     */
    _registerTimer_: function _registerTimer_() {
        var self = this;
        this.timer = setInterval(function () {
            //如果上条消息已经处理完成，并且消息队列中还有消息
            if (!self.isProcessing && self.msgArr.length > 0) {
                self._processMsg_(); //处理一条消息
            }
            //如果消息队列为空，就清除定时器
            if (self.msgArr.length === 0) {
                clearInterval(self.timer);
                self.timer = null;
            }
        }, 100);
    },

    /**
     * 进行处理，如果真的处理了，返回 true, 否则返回 false
     * @private
     * @returns {boolean} -- false 表示消息队列中还有消息，但本次没有处理其中任何一条， true表示本次处理了一条消息
     */
    _processMsg_: function _processMsg_() {

        var index = -1,
            //本次要处理的消息，位于消息队列中的位置，如果是-1则表示没有要处理的消息
        msgArr = this.msgArr,
            //消息队列(数组)
        data; //要处理的那条消息，注意data是一个数组，而不是一个对象，即使data中只有一条消息

        for (var i = 0; i < msgArr.length; i++) {
            var msg = msgArr[i];
            //如果设置了条件
            if (msgCondition[msg.type]) {
                //注意，msgCondition是一个全局变量
                var arr = msgCondition[msg.type];
                var isNeedCache = true;
                for (var j = 0; j < arr.length; j++) {
                    if (!window.CONDITIONS[msgCondition[msg.type][j]](msg)) {
                        //如果有一个条件不满足
                        isNeedCache = false;
                        break;
                    }
                }
                if (isNeedCache) {
                    //如果所有条件都满足，就直接执行
                    index = i;
                }
            } else {
                //如果没有条件就直接处理
                index = i;
            }
        }

        //删除那条消息, 如果删除成功，就返回被删除的元素组成的数组，当 index == null 时删除第0个元素，-1删除最后一个元素
        if (this.msgArr[index]) {
            //如果存在那条消息，就将其赋值给data变量，并将其从队列中删除
            data = this.msgArr.splice(index, 1);
        }

        if (data !== msgArr && data && data.length === 1) {
            this.isProcessing = true; //标记正在处理中
            console.log('3开始处理 ' + msg.type + ' 消息！');
            this.controllerMap.accessController(data[0]); //调用各自控制器进行处理
            return true;
        }

        return false;
    }

});

module.exports = MsgCache;

cc._RFpop();
},{}],"msgComm":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8fae6BIwkJNR6gJzmTvIS0R', 'msgComm');
// scripts\common\controller\msgComm.js

'use strict';

var User = require('user');
var Room = require('room');

var msgComm = {
    /**
    * 初始始化 客户对象 及 大厅页面 用于 正常的登录 还有重构时
    * @param {object} userInfo --
       {   cards: 1001,  faceIcon:"5350b36d56b6e054.jpg",  nickName:"玩家10102", openId:"10102", userId:10102,
           isVip: true, sex: 1, ip: '192.168.0.104', roomId:'', state: 1, ready: true,
       },
    */
    initHall: function initHall(userInfo) {
        var clientUser = new User({
            attr: {
                roomCards: userInfo.cards, //房卡数  1000
                faceIcon: userInfo.faceIcon, //头像  "5350b36d56b6e054.jpg"
                nickName: userInfo.nickName, //昵称
                openId: userInfo.openId, //用户的openId
                userId: userInfo.userId, //用户ID
                sex: userInfo.sex === 1 ? 'man' : 'woman', //1表示男 2表示女
                ip: userInfo.ip,
                isVip: userInfo.isVip, //是否是vip
                isClient: true }
        });

        clientUser.combineSend(); //将发送消息的对象绑定到自己身上

        window.clientUser = clientUser; //存入全局

        //在此处增加信息分享
        clientUser.send({ type: 'share', message: window.location.href }); //发送的消息

        //显示用户的信息
        getGI('hall').display({
            nickName: clientUser.attr('nickName'), //昵称
            userId: clientUser.attr('userId'), //用户Id
            headIcon: clientUser.attr('faceIcon'), //头像图标
            isVip: clientUser.attr('isVip'), //是否是vip
            roomCardNum: clientUser.attr('roomCards') });
    },

    /**
    * 初始化房间信息
    * @private
    * @param {Array} joinMessage -- 房间信息及四个用户的所有详细信息
    */
    initRoom: function initRoom(joinMessage, callback) {
        var roomInfo = joinMessage.room; //房间信息
        var roomUsers = joinMessage.players; //先前已经加入房间的用户(包括自己)

        if (!window.room) {
            //如果自己还没进入房间
            console.log('如果自己还没进入房间');

            this.createRoom(roomInfo); //创建一个房间
            this.initSetting(roomInfo); //初始化玩法的设置

            var self = this;
            //加载房间界面，注意，要界面显示完成后再显示头像
            cc.director.loadScene("room", function () {
                //界面加载完成后
                console.log('场景加载完成');
                window.audio.stopBgMusic(); //停止背景音乐
                window.audio.playMusic('playingBg'); //播放大厅页的背景音乐
                self.roomLoaded(joinMessage);
                callback && callback();
            });
        } else {
            //后来，每加入一个人，就创建与其相应的用户，并显示出来
            this.addUser(joinMessage, 'one'); //注意，最新加入进来的不一定是最后一个
            callback && callback();
        }
    },

    createRoom: function createRoom(roomInfo) {
        var currentJuNum = roomInfo.totalQuanNum === 1 || roomInfo.totalQuanNum === 2 ? 1 : roomInfo.totalQuanNum === 3 || roomInfo.totalQuanNum === 4 ? 0 : 1;
        //创建一个房间
        window.room = new Room({
            roomId: +roomInfo.roomId, //房间号
            totalQuan: +roomInfo.totalQuanNum, //总局数
            currentJuNum: +roomInfo.useJuNum, //当前局数
            currentQuan: currentJuNum });
    },

    roomLoaded: function roomLoaded(joinMessage) {
        TOOL.resetData();
        //播放背景音乐
        // getGI('musicVolume') && audio.play('playingBg.mp3', true); //循环播放

        setGI('roomSceneFinished', true); //标记本场景已加载完成，当第四个人加入进来时，会同时收到 join和start消息，可能在场景还未加载完成时就运行start消息而报错

        //组合其它类的方法
        room.combineAction();
        // console.log(room);
        //显示房间号和当前局数
        room.displayRoomInfo({
            roomId: room.roomId, //房间号
            inviteBtn: 'show', //中间的邀请按钮显示
            juCount: { currentJuNum: room.currentQuan, totalJuNum: room.totalQuan }, //显示当前局数和总局数
            arithmetic: 'setting' });

        //更改分享link，加上房间号
        TOOL.replaceShareUrl(room.roomId);

        //创建比自己先进入房间的所有用户，并显示出来
        this.addUser(joinMessage);

        if (clientUser.attr('zhuang')) {
            //如果自己是庄家，应该隐藏退出房间的按钮
            room.displayRoomInfo('exit', 'hide');
        }

        room.attr('period', 'readying'); //进入readying阶段
    },

    /**
     * 说明：加入房间时，按时间先后顺序 按顺时针加入
     * 当前用户进入房间时，已经有其它人进来了，所有一次性要将之前加入进来的人的用户对象全部创建出来
     * @private
     * @param {Array} roomUsers -- 房间中所有用户的数组，其中最后一个是最新加入的那个用户
     */
    addUser: function addUser(joinMessage, one) {
        var roomInfo = joinMessage.room; //房间信息
        var roomUsers = joinMessage.players; //先前已经加入房间的用户(包括自己)
        var meIndex = joinMessage.meIndex; // 自己位于本数组中的下标，即roomUsers[meIndex]就是客户的用户信息
        var user; //用户对象

        if (one === 'one') {
            var bigUserInfo = roomUsers[meIndex];
            user = new User();
            this._andOneUser_(user, bigUserInfo, roomInfo);
        } else {
            var len = roomUsers.length; //已经加入房间的用户总个数(包括自己)

            clientUser.attr('joinOrder', roomUsers[meIndex].order - 1); //注意，一定要先设置这个，因为clientUser在数组中的位置是随机的

            for (var i = len - 1; i > -1; i--) {
                //注意，无论什么情况下， roomUsers 中不会有 null， 即 roomUsers[i]一定是一个用户
                var bigUserInfo = roomUsers[i];

                if (i !== meIndex) {
                    //如果不是自己， 因为自己已经创建了用户对像，所以不用再创建
                    user = new User();
                } else {
                    user = clientUser; //对于客户，在登录时，就已经创建了，这里只是增加一些信息
                }

                this._andOneUser_(user, bigUserInfo, roomInfo);
            }
        }
    },

    /**
     */
    _andOneUser_: function _andOneUser_(user, bigUserInfo, roomInfo) {
        var order = bigUserInfo.order - 1; //当前用户的加入的顺序号
        var clientJoinOrder = clientUser.attr('joinOrder'); //客户的加入的顺序号
        var positionOrder = (order < clientJoinOrder ? 4 : 0) - (clientJoinOrder - order);
        var userInfo = bigUserInfo.userInfo;

        user.attr({
            roomCards: userInfo.cards, //房卡数  1000
            faceIcon: userInfo.faceIcon, //头像  "5350b36d56b6e054.jpg"
            nickName: userInfo.nickName, //昵称
            openId: userInfo.openId, //用户的openId
            userId: userInfo.userId, //用户ID
            joinOrder: order, //自己是第几个加入房间 取值 [0,3]。0表示创建房间，1表示第一个加入房间，2表示第二个加入房间，3表示第三个加入房间
            positionOrder: positionOrder, //自己在房间中的位置号 取值 [0,3]。0表示自己这方，1表示左边， 2表示对面，3表示右边
            sex: userInfo.sex === 1 ? 'man' : 'woman', // 是男的还是女的, 'man' 或 'woman'
            ip: userInfo.ip, //用户的ip地址
            whoseVoice: userInfo.sex === 1 ? 'b' : 'g', //是哪个人的声音 可取 b g
            isVip: userInfo.isVip, //是否是vip
            state: userInfo.state, // 0-离线 1-在线
            isReady: userInfo.ready, //是否准备好了
            isHost: roomInfo.creator == userInfo.userId, //房主
            isActive: roomInfo.activeIndex === order, //是否是活动用户
            zhuang: userInfo.userId == roomInfo.zhuangId });

        var outAllCards = bigUserInfo.outAllCards;
        var firstOutCard = outAllCards && outAllCards.length > 0 ? outAllCards[0] : '';

        //组合其它类的方法
        user.combineAction();

        user.attr('naPaiInfo').cardName = firstOutCard; //记录拿牌信息

        //显示用户的头像信息
        this._displayHead_(user);
        room.addUser(user); //用户加入房间
    },

    /**
     * 显示一个用户的头像信息
     * @private
     * @param {Array} roomUsers -- 本次join消息中的所有用户
     * @param {User} user -- 要显示的用户
     * @param {number} index -- 用户的加入顺序
     * @param {boolean} zhuang -- 是否是庄家
     */
    _displayHead_: function _displayHead_(user) {

        var userData = user.attr({
            faceIcon: undefined, zhuang: undefined, sex: undefined, isHost: undefined, state: undefined,
            isReady: undefined, nickName: undefined, roomScore: undefined
        });

        user.displayHead({
            zhuang: userData.zhuang ? 'show' : 'hide', //显示 庄家, 注意：传入 'show'表示显示，'hide'表示隐藏
            faceIcon: userData.faceIcon, //显示头像  "5350b36d56b6e054.jpg"
            nickName: userData.nickName, //显示昵称
            score: userData.roomScore, //分数是0 , 注意：传入 'show'表示显示，'hide'表示隐藏, 传入数字表示更改并显示
            ok: userData.isReady ? 'show' : 'hide', //不显示ok
            leaveLine: userData.state === 0 ? 'show' : 'hide', //不显示离线
            isHost: userData.isHost ? 'show' : 'hide' });
    },

    /**
     * 房间的玩法的初始化
     * @private
     * @param {Array} opt -- 玩法的选择结果 { ju: 1, opt: [1,2]}
     * //ju：8局--1 16局--2， 分数打法：100分--3 200分--4
    * //opt[0]: 2分--1 4分--2 8分--3
    *  //opt[1]: 单游不能平到--1 双游不能平到--2
     */
    initSetting: function initSetting(joinMessage) {
        var opt = JSON.parse(joinMessage.opt);

        var opt1 = joinMessage.totalQuanNum,
            opt2 = opt[0],
            opt3 = opt[1];
        opt1 = opt1 === 1 ? '8局' : opt1 === 2 ? '16局' : opt1 === 3 ? '100分' : opt1 === 4 ? '200分' : 0;

        opt2 = opt2 === 1 ? '2分' : opt2 === 2 ? '4分' : opt2 === 3 ? '8分' : 0;

        opt3 = opt3 === 1 ? '单金不能平到' : opt3 === 2 ? '双金不能平到' : 0;

        room.attr({ ju: opt1, diFeng: opt2, fanXin: opt3 });
    },

    /**
     * 从四个用户中找到客户的索引号
     * @private
     * @returns {number} i -- 客户在players数组中的位置
     */
    getMeInfo: function getMeInfo(players, meUserId) {
        for (var i = 0; i < players.length; i++) {
            if (players[i].userInfo.userId === meUserId) {
                return i;
            }
        }
    }
};

module.exports = msgComm;

cc._RFpop();
},{"room":"room","user":"user"}],"msgConfig":[function(require,module,exports){
"use strict";
cc._RFpush(module, '96836IX0t1EdL9d8nNvlApO', 'msgConfig');
// scripts\common\msgMng\msgConfig.js

'use strict';

//消息的协议
var messageType = {
    'start': 1, //开始
    'ready': 2, //房间人员都准备好了，当四个人都发了ready消息后，返回一个start消息
    'join': 3, //加入房间
    'login': 4, //登录
    'create': 5, //创建房间
    'heart': 6, //心跳消息
    'outCard': 7, //出牌
    'hu': 8, //胡牌
    'napai': 9, //拿牌
    'huang': 10, //荒庄
    'pong': 11, //碰
    'gang': 12, //杠
    'chi': 13, //吃成功时发的消息
    'ziMo': 15, //自摸胡牌
    'sanJingDao': 16, //三金倒
    'youJing': 17, //游金
    'shuangYou': 18, //双游
    'sanYou': 19, //三游
    'juScore': 20, //每局结束得分统计
    'roomScore': 21, //房间结束得分统计
    'tianHu': 22, //天胡胡牌

    'youJingZhong': 23, //开始单游
    'shuangYouZhong': 24, //开始双游
    'sanYouZhong': 25, //开始三游

    'dismiss': 30, //收到四条解散消息后的返回消息
    'dismissing': 31, //申请解散房间时发送的消息
    'record': 32, //查询战绩记录发的消息
    'leaveLine': 33, //每当一个用户离线后，其它所有人都会收到一条离线消息(服务器判断的是否离线)
    'reConnect': 34, //当自己的网络恢复后，会发这个消息，然后服务器会转发给其它人
    'rebuild': 35, //重连后，重新构建游戏场景
    'exitRoom': 36, //因加错房间后，可以点击退出房间(如果房主退出了，就解散房间)
    'buHua': 38, //补花消息
    'share': 37, //分享
    'error': 99, //异常

    //以下是自己定义的消息，后台只会转发这种类型的消息，不会进行任何处理

    'guo': 50, //一个人出牌后，其它几个人对这张牌有操作，但都选的是过，则每个人都会收到一条过的消息
    'buGang': 51, //选择补杠后要广播出去
    'anGang': 52, //选择暗杠后要广播出去
    'mingGang': 58, //明杠成功发的消息
    'face': 55, //表情
    'word': 56 };

//控制器地图
var controllerMap = {
    error_controller: ['join', 'error', 'ready'], //这个比较特殊，必须有这个属性，但其中的消息可有可无，不会被使用

    napai_controller: ['napai', 'buHua'], // 文件名：[消息名1, 消息名2, 消息名3]
    juEnd_controller: ['juScore', 'roomScore'],
    msgSend_controller: ['word', 'face'],
    operate_controller: ['buGang', 'anGang', 'ting', 'hu', 'mingGang', 'chi', 'pong', 'youJing', 'shuangYou', 'sanYou', 'tianHu', 'ziMo', 'sanJingDao', 'guo'],
    outCard_controller: ['outCard', 'youJingZhong', 'shuangYouZhong', 'sanYouZhong'],
    start_controller: ['login', 'start'],
    dismiss_controller: ['ready', 'dismissing', 'dismiss'],
    join_controller: ['create', 'join'],
    buGangCheck_controller: ['wantBuGang', 'buGangCheck'],
    record_controller: ['record'],
    isOnline_controller: ['leaveLine', 'reConnect', 'exitRoom'],
    rebuild_controller: ['rebuild'],
    share_controller: ['share', 'heart']
};

/**
 * 根据window.messageType对象 中'start': 1， 再增加一个属性  '1': 'start'
 */
(function (messageType) {
    for (var key in messageType) {
        messageType[messageType[key]] = key;
    }
})(messageType);

/**
 * 对收到消息进行字段名的转换
 * 要发送的数据： {m:{}, p:389892, t:5}
 * 收到的数据：   {errMsg: string, type: string, message: some, success: boolean}
 * @public
 * @param {Object} dataObj -- 要处理的(转换的)数据对象
 * @returns {Object} 转换后的数据对象, 如果是发送的数据，则返回一个新对象，否则 在原对像上更改
 */
messageType.convert = function (dataObj) {
    var resultData;
    if ('s' in dataObj) {
        //说明dataObj是收到的数据

        //{e: null, s: true, m: {aa:34}, t:5} --> {errMsg: null, success: true, message: {aa:34}, type: 'login'}
        resultData = dataObj;
        resultData.errMsg = dataObj.e; //错误信息 {string}
        resultData.type = this[dataObj.t]; //类型名字 {string}
        resultData.message = dataObj.m; //消息内容 {object}
        resultData.success = dataObj.s; //是否成功, {boolean}
    } else {
        //说明dataObj是要发送的数据

        //{openId: null,  message: {aa:34}, type: 'login'} --> { m: {ab: 39}, p: 38992833, t:5}
        resultData = {};
        resultData.t = this[dataObj.type]; //消息类型 {string}
        resultData.m = dataObj.message; //消息内容 {object}
        resultData.p = dataObj.openId; //发送人的openId, {string}
    }

    return resultData;
};

module.exports = { messageType: messageType, controllerMap: controllerMap };

cc._RFpop();
},{}],"msgSend_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'b2697dGIztLu45tAALfqPh8', 'msgSend_controller');
// scripts\common\controller\messageController\msgSend_controller.js

'use strict';

/**@class
 * @classdesc 聊天信息
 */
var MsgSend_controller = cc.Class({
    name: 'MsgSend_controller',

    /**
    * 处理文字消息
    * data: {  e | errMsg: null,
               m | message: {t:56, m: '你真是太牛了', 'p': '10194'} //常用语的信息
               s | success: true, t | type: 56, word
           }
    */
    word_controller: function word_controller(data) {
        console.log('执行 word_controller...');
        var message = data.message;
        var user = room.getUser(message.p);
        user.say({ type: 'word', value: message.m, whoseVoice: user.attr('whoseVoice') });

        socket.finishMsg('word'); //标记本条消息处理完成
    },

    /**
    * 处理表情消息
    * data: {  e | errMsg: null,
               m | message: {t:55, m: 'EE', 'p': '10194'} //表情名字的信息
               s | success: true, t | type: 55, face
           }
    */
    face_controller: function face_controller(data) {
        console.log('执行 face_controller...');

        var message = data.message;
        var user = room.getUser(message.p);

        user.say({ type: 'face', value: message.m });

        socket.finishMsg('face'); //标记本条消息处理完成
    }

});

module.exports = MsgSend_controller;

cc._RFpop();
},{}],"msgStartRun":[function(require,module,exports){
"use strict";
cc._RFpush(module, '803ebAkzBlDUL8t+K6WerW8', 'msgStartRun');
// scripts\common\controller\msgStartRun.js

'use strict';

var msgStartRun = {
        /**
        * 开始初始化并运行游戏界面
        * @private
        */
        startRun: function startRun(message, isOnline) {
                console.log('执行 _startRun_...');

                room.goldCard = message.room.goldCard; //设置金牌

                //初始化用户的牌的数据
                this._initUser_(message);

                //初始化房间数据
                this._initRoom_(message.room.cardNum);

                //显示每个人的牌
                this._showCards_(message);

                //由于创建房间时，默认的活动用户是庄家，所有在所有人加入房间过后，在这里手动改成指定的活动用户
                var meJoinOrder = message.players[message.meIndex].order - 1,
                    //自己的加入的顺序号
                activeIndex = message.room.activeIndex; //活动用户的加入的顺序号

                room.acitveIndex = activeIndex >= meJoinOrder ? activeIndex - meJoinOrder : 4 + activeIndex - meJoinOrder; //注意这里绝不能用 meIndex来计算

                //指向当前用户并倒计时
                room.displayPointer(room.acitveIndex);

                room.setActiveUser(room.acitveIndex); //设置下一个用户

                // this._calc_(message); //计算自己是不是该出牌或拿牌

                var handCardNum = message.players[message.meIndex].handCards.length;

                if (handCardNum % 3 === 2) {
                        //如果该自己出牌
                        //指向出牌用户
                        room.displayPointer(clientUser.attr('positionOrder'));
                        room.setActiveUser(clientUser.attr('positionOrder')); //设置下一个用户
                        clientUser.setAttr('isCanPlay', true); //如果客户，设置自己能否出牌
                }

                if (!isOnline) {
                        clientUser.sleep(); //自己不能有任何操作
                } else {
                        room.acitveIndex === 0 && clientUser.wake();
                }

                setGI('rebuildFinished', true);
                socket.finishMsg('rebuild'); //标记本条消息处理完成

                room.attr('isCanShowAllResult', false); //不可以显示总结算界面
        },

        /**
        * 初始化用户的牌的数据
        * @private
        */
        _initUser_: function _initUser_(message) {

                var self = this,
                    maxLen = 0;
                room.forEach(function (user, index) {
                        var userInfo = message.players[user.attr('joinOrder')];

                        //创建管理牌的对象
                        user.combineMahjong();

                        var handCards = userInfo.handCards;
                        handCards = handCards[0] === '' ? handCards.length : handCards;

                        TOOL.converOperate(userInfo.optMsgs);

                        var huaCards = userInfo.huaCards;
                        if (huaCards && huaCards.length > 0) {
                                userInfo.optMsgs.push({ p: user.userId, t: 'buHua', c: '', from: user.userId, m: huaCards });
                        }
                        //添加一副牌
                        user.setCards({
                                handCards: handCards, outCards: userInfo.outCards, outAllCards: userInfo.outAllCards,
                                mingCards: userInfo.optMsgs, huaCards: null
                        });

                        //隐藏ok
                        user.displayHead({ 'ok': 'hide' });
                });
        },

        /**
         * 初始化房间数据
         * @private
         */
        _initRoom_: function _initRoom_(restNum) {

                room.attr('period', 'playing');

                //将头像运动到一边去
                room.runHeadNoTime();
                console.log('头像运动！！！！');
                room.displayRoomInfo({
                        playingLayer: 'show', //显示麻将
                        // 'goldCard': 'hide', //
                        timer: 'show', //中间的倒计时框显示
                        juCount: { currentJuNum: room.currentJuNum, totalJuNum: room.totalQuan }, //显示当前局数和总局数
                        restCardNum: 'show' });

                room.diceToggle('hide'); //隐藏骰子，显示倒计时

                //指向当前用户并倒计时
                room.displayPointer(room.acitveIndex);

                room.setActiveUser(room.acitveIndex); //设置下一个用户

                room.attr('readyArr').length = 0; //清空准备的人的数组

                room.attr('isCanShowAllResult', false); //不可以显示总结算界面
                room.attr('restCardNum', restNum); //开局后，剩余牌为83张
        },

        /**
         * 没有定时器， 直接显示牌
         */
        _showCards_: function _showCards_() {

                room.forEach(function (user) {
                        user.mahjongMng.mahView.convertHM('hand');
                        user.showCards('order'); //显示所有手牌
                        room.displayRoomInfo('goldCard', room.goldCard);

                        user.showCards('allOut'); //显示所有出的牌
                        user.showCards('allMing'); //显示所有明牌
                });

                setGI('buHuaOver', true);
        }

};

module.exports = msgStartRun;

cc._RFpop();
},{}],"msgsMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '83429g++d9DUIAabrYr25C8', 'msgsMng');
// scripts\server\msgsMng.js

'use strict';

var msgs = require('msgs');
var ServerControllerMapMng = require('serverControllerMapMng');
/**
 * @class
 * @desc
 */
var MsgsMng = cc.Class({
    name: 'MsgsMng',

    /**
     * @constructor
     */
    ctor: function ctor() {
        this.msgs = msgs;
        this.msgTimer = null;
        this.serverController = new ServerControllerMapMng({ controllerMap: window.serverControllerMap });
    },

    run: function run() {
        var self = this;
        this.msgTimer = setInterval(function () {
            if (self.msgs.length === 0) {
                clearInterval(self.msgTimer);
                this.msgTimer = null;
            } else {
                var msg = self.msgs.shift();
                console.log(msg);
            }
        }, 2000);
    },

    receive: function receive(msgObj) {
        //this.msgs.push(msgObj);
        // t、p、m --> type、openId、message
        msgObj = window.serverMessageType.convert(msgObj); //对收到消息进行字段名的转换
        this.serverController.accessController(msgObj);
    },

    send: function send(msgObj) {
        // errMsg、type、message、success -->  e、t、m、s
        !msgObj.errMsg && (msgObj.errMsg = null);
        !msgObj.success && (msgObj.success = true);
        msgObj = window.serverMessageType.convert(msgObj); //对收到消息进行字段名的转换
        window.socket.onmessage(msgObj);
    }

});

module.exports = MsgsMng;

cc._RFpop();
},{"msgs":"msgs","serverControllerMapMng":"serverControllerMapMng"}],"msgs":[function(require,module,exports){
"use strict";
cc._RFpush(module, '9e157uQ/E9MH4ZnI5e7zDqA', 'msgs');
// scripts\server\msgs.js

"use strict";

var msgs = [{ t: '4', //login
  m: { cards: 1000, faceIcon: "5350b36d56b6e054.jpg", nickName: "玩家10004", openId: "10004",
    userId: 10004, sex: 1, ip: '192.168.0.101' },
  e: null }];

module.exports = msgs;

cc._RFpop();
},{}],"napai_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f8ddauJu25G2ZfihAD9ZrnI', 'napai_controller');
// scripts\common\controller\messageController\napai_controller.js

'use strict';

/**@class
 * @classdesc 拿牌
 */
var InCard_controller = cc.Class({
        name: 'InCard_controller',

        /**
         * 拿牌
         * data: {  e | errMsg: null | [opt1, opt2],  //注意，当牌拿完了，收到的card为null，而不是''.
                    m | message: {c: '' | 'w2', cardNum: 83//剩余牌数, who: "10129"//拿牌人的openId} //拿牌信息,只有发了拿牌请求的人card不为空
                    s | success: true, t | type: 50, operate
                }
        */
        napai_controller: function napai_controller(data) {
                var msg = data.message;
                var card = msg.c; //拿到的牌
                var who = msg.who; //谁拿的牌

                // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息

                //决定牌是从哪里拿的
                // this._getOneCard_(msg);
                var user = room.getUser(who); //注意这里是who而不是openId

                room.attr('restCardNum', msg.cardNum); //记录剩余牌数

                room.setActiveUser(who); //设置拿牌的用户为活动用户
                room.displayPointer(room.getNextActiveIndex(who)); //指向当前用户并倒计时

                //如果牌拿完了
                if (card == null) {
                        // clientUser.juEnd(null); //处理一局结束
                        // var next = room.zhuang - 1 < 0 ? 3 : room.zhuang - 1
                        // room['nextZhuang'] = room.getUser( next ).attr('openId') ; //下一局的庄家, 逆时针方向的下一个

                        socket.finishMsg('napai'); //标记本条消息处理完成
                        return;
                }

                if (card) {
                        clientUser.attr('naPaiInfo').cardName = card;
                }

                TOOL.deleteStart(user, data.e); //去掉其中的 开始单游 开始双游 开始三游 并记录下来

                user.inCard(card, msg.cardNum, data.e);

                if (room.msgIsMeSend(who)) {
                        //如果是自己拿的牌
                        var type = card.charAt(0);
                        if (card !== room.goldCard && (type === 'i' || type === 'h')) {
                                //是花牌 且 不是金牌
                                clientUser.send({ type: 'buHua', message: card }); //发送补花消息
                        }
                }

                socket.finishMsg('napai'); //标记本条消息处理完成
        },

        /**
         * 补花消息
         * data: {  e | errMsg: null | [opt1, opt2],  //注意，当牌拿完了，收到的card为null，而不是''.
                    m | message: {h: 'i2' //花牌, c: '' | 'w2', cardNum: 83//剩余牌数, who: "10129"//拿牌人的openId} //拿牌信息,只有发了拿牌请求的人card不为空
                    s | success: true, t | type: 50, operate
                }
        */
        buHua_controller: function buHua_controller(data) {
                var msg = data.message;
                var card = msg.c; //拿到的牌
                var hua = msg.h; //花牌
                var who = msg.who; //谁拿的牌

                // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息

                var user = room.getUser(who); //注意这里是who而不是openId

                room.attr('restCardNum', msg.cardNum); //记录剩余牌数

                room.setActiveUser(who); //设置拿牌的用户为活动用户
                room.displayPointer(room.getNextActiveIndex(who)); //指向当前用户并倒计时

                //如果牌拿完了
                if (card == null) {
                        // clientUser.juEnd(null); //处理一局结束
                        // var next = room.zhuang - 1 < 0 ? 3 : room.zhuang - 1
                        // room['nextZhuang'] = room.getUser( next ).attr('openId') ; //下一局的庄家, 逆时针方向的下一个

                        socket.finishMsg('buHua'); //标记本条消息处理完成
                        return;
                }

                if (card) {
                        clientUser.attr('naPaiInfo').card = card;
                }

                user.operateItem({ p: who, t: 'buHua', c: hua, from: who, m: [hua] });

                TOOL.deleteStart(user, data.e); //去掉其中的 开始单游 开始双游 开始三游 并记录下来
                user.inCard(card, msg.cardNum, data.e);

                socket.finishMsg('buHua'); //标记本条消息处理完成
        }
});

module.exports = InCard_controller;

cc._RFpop();
},{}],"node_hall_broadcast":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c87831bGzNI1Zq/V6FQqq85', 'node_hall_broadcast');
// scripts\hall\node_hall_broadcast.js

'use strict';

/**
 * @class
 * @classdesc 本类挂载在 hall.sence场景中的 Canvas 上
 */
cc.Class({
    extends: cc.Component,

    /**
    * hall.sence场景 中的 Canvas节点 加载完成后 的回调
    * @callback
    */
    onLoad: function onLoad() {

        this.msgNode = cc.find('hallMain/namePanel/broadcastBg/maskNode/broadStr', this.node); //广播的文字节点
        this.msgNode.x = (this.msgNode.parent.width + this.msgNode.width) / 2;

        this.num = 0;
        this.isFinished = true;
        this.broadcastAni();
        this.timer = setInterval(this.broadcastAni.bind(this), 30000);
    },

    onDestroy: function onDestroy() {
        clearInterval(this.timer);
        this.isFinished = true;
        console.log('销毁了...');
    },

    /**
     * 大厅页面的信息公告的动画播放
     * @private
     * @param {string} msg -- 可省略， 要显示的内容
     */
    broadcastAni: function broadcastAni(msg) {
        this.num++;
        if (this.num > 15) {
            clearInterval(this.timer);
            this.isFinished = true;
        }

        var msgNode = this.msgNode;
        if (msg) {
            msgNode.getComponent(cc.Label).string = msg; //更改提示信息
            clearInterval(this.timer);
            this.timer = setInterval(this.broadcastAni.bind(this), 30000);
            msgNode.x = (msgNode.parent.width + msgNode.width) / 2;
        }

        if (!this.isFinished) {
            //如果没有完成播放，就下次再播
            return;
        }

        this.isFinished = false;

        var duration = msgNode.width / 100 * 1.5;
        var startPosition = msgNode.getPosition();
        var endPosition = startPosition.sub(cc.v2(msgNode.parent.width + msgNode.width, 0));
        console.log('运行了广告！');
        msgNode.runAction(cc.sequence(cc.delayTime(1.5), cc.moveTo(duration, endPosition), cc.callFunc(function () {
            msgNode.setPosition(startPosition);
            this.isFinished = true;
        }, this)));
    }
});

cc._RFpop();
},{}],"node_hall_display":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'af034VHtWtNTIgjTVE/uQlq', 'node_hall_display');
// scripts\hall\node_hall_display.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var InputNumPanel = require('inputNumPanel');
/**
 * @class
 * @classdesc 本类挂载在 hall.sence场景中的 Canvas 上
 */
cc.Class({
    extends: cc.Component,

    properties: {
        hallMain_atlas: cc.SpriteAtlas },

    /**
     * 显示一个值,或一组值
     * @public
     * @param {string|object} name -- 属性名, 如果传入一个对象，则不再读取第二个参数
     * @param {string} value -- 属性值
     */
    display: function display(name, value) {
        if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
            for (var prop in name) {
                this._displayOne_(prop, name[prop]);
            }
        } else {
            this._displayOne_(name, value);
        }
    },

    /**
     * 显示一个属性的值
     * @private
     * @param {string} name -- 属性名
     * @param {string} value -- 属性值
     */
    _displayOne_: function _displayOne_(name, value) {
        switch (name) {
            case 'nickName':
                cc.find('hallMain/namePanel/userInfo/nickName', this.node).getComponent(cc.Label).string = value;
                break;
            case 'userId':
                cc.find('hallMain/namePanel/userInfo/userId', this.node).getComponent(cc.Label).string = 'ID: ' + value;
                break;
            case 'headIcon':
                TOOL.setUserHeadIcon(cc.find('hallMain/namePanel/userInfo/headIcon', this.node), value);
                break;
            case 'roomCardNum':
                cc.find('hallMain/namePanel/userInfo/diamondFrame/num', this.node).getComponent(cc.Label).string = value;
                break;
            case 'isVip':
                value = value ? 'vipGold' : 'vipGray';
                cc.find('hallMain/namePanel/userInfo/vip', this.node).getComponent(cc.Sprite).spriteFrame = this.hallMain_atlas.getSpriteFrame(value);
                break;
        }
    },

    /**
     * 显示或隐藏历史记录
     * @public
     * @param {string} isShow -- 'show'表示显示，'hide'表示隐藏
     */
    showRecordAlert: function showRecordAlert(isShow) {
        isShow = isShow === 'show' ? true : false;
        this.node.getChildByName('recordAlert').active = isShow; //显示 历史记录弹出框
        this.node.getChildByName('main').active = !isShow; //隐藏背景图层
    },

    /**
    * hall.sence场景 中的 Canvas节点 加载完成后 的回调
    * @callback
    */
    onLoad: function onLoad() {
        //创建数字输入面板的对象，并添加事件
        this.inputNumPanel = new InputNumPanel();
        //存入全局中
        setGI('hall', this);

        console.log('调整hall的高度');
        this.node.height = cc.winSize.height;
        var self = this;

        cc.view.setResizeCallback(function () {
            console.log('调整hall的高度');
            self.node.height = cc.winSize.height;
            console.log(cc.winSize);
            // getGI('hall').errorReminder( cc.winSize.height + '' + cc.winSize.width);
        });
    },

    /**
     * 当在重新构造打牌的场景时，会先显示大厅页面，用户可能会点击创建房间或加入房间按钮，所以需要先禁用本页面，然后再启用
     * @public
     */
    disableHall: function disableHall() {
        cc.find('Canvas/hallMain/medium/createIcon').getComponent(cc.Button).interactable = false; //禁用创建房间按钮
        cc.find('Canvas/hallMain/medium/joinIcon').getComponent(cc.Button).interactable = false; //禁用加入房间按钮
        cc.find('Canvas/hallMain/medium/friendCreate').getComponent(cc.Button).interactable = false; //禁用替好友创建房间按钮
        cc.find('Canvas/hallMain/medium/mahjongHallIcon').getComponent(cc.Button).interactable = false; //禁用麻将大厅按钮
    },

    /**
     * 加入房间出错时的提示显示 (没有找到该房间，可能房主已退出, 或房间已满)，socket中会用
     * @public
     * @param {string} messageStr -- 必须，要显示的提示信息
     */
    errorReminder: function errorReminder(messageStr) {
        this.getComponent(cc.Animation).stop('reminder'); //停止动画
        clearTimeout(timer);
        var reminderNode = cc.find('Canvas/reminder'); //显示提示信息的节点
        var labelNode = reminderNode.getChildByName('describe');
        labelNode.getComponent(cc.Label).string = messageStr; //更改提示文字
        this.getComponent(cc.Animation).play('reminder'); //播放提示动画
        var self = this;
        var timer = setTimeout(function () {
            if (!self) {
                clearTimeout(timer);
                return;
            }
            self.getComponent(cc.Animation).stop('reminder'); //停止动画
            clearTimeout(timer);
        }, 4000);
    },

    /**
     * 开始 加载过程中的动画
     * @public
     * @param {string} str -- 必须，start表示开始，end表示结束
     */
    loading: function loading(periodStr) {
        var loadingNode = cc.find('Canvas/loading');
        switch (periodStr) {
            case 'start':
                loadingNode.active = true;
                this.getComponent(cc.Animation).play('loading'); //播放加载中的动画
                break;
            case 'end':
                this.getComponent(cc.Animation).stop('loading'); //停止加载中的动画
                loadingNode.active = false;
                break;
        }
    }
});

cc._RFpop();
},{"inputNumPanel":"inputNumPanel"}],"node_helpAlert":[function(require,module,exports){
"use strict";
cc._RFpush(module, '92f1diDcptDVJHKTRecU6rm', 'node_helpAlert');
// scripts\hall\node_helpAlert.js

'use strict';

/**
 * @class
 * @classdesc 本类挂载在 hall.sence场景中的 helpAlert节点 上
 */
cc.Class({
    extends: cc.Component,

    properties: {},

    /**
    * hall.sence场景中的 helpAlert节点 上 的回调
    * @callback
    */
    onLoad: function onLoad() {
        getGI('hall').loading('end'); //加载完成
    }

});

cc._RFpop();
},{}],"node_recordAlert":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd2fbe7PCRBKY4Q8TqlHfXOH', 'node_recordAlert');
// scripts\hall\node_recordAlert.js

'use strict';

/**
 * @class
 * @classdesc 本类挂载在 hall.sence场景中的 Canvas 上
 */
cc.Class({
    extends: cc.Component,

    properties: {
        recordItemPref: cc.Prefab },

    /**
     * 显示历史记录
     * @public
     * @param {Array} dataArr -- 要显示的信息数组
     * dataArr = [
     *          {roomId: '6389292', time: '2016-11-29 14:03',
     *          usersInfo: [ {nickName: '如花',score: -28}, {nickName: '如花',score: -28}, {nickName: '如花',score: -28}, {nickName: '如花',score: -28}]},
     *          {roomId: '6389292', time: '2016-11-29 14:03',
     *          usersInfo: [ {nickName: '如花',score: -28}, {nickName: '如花',score: -28}, {nickName: '如花',score: -28}, {nickName: '如花',score: -28}]},
     * ]
     * @param {string} method -- 可省略，省略时表示覆盖，如果传入 'append'，表示追加
     * @example aa
     */
    showRecords: function showRecords(dataArr, method) {

        var recordBox = cc.find('recordAlert/alertContent/recordBg/scrollView/view/content', this.node);
        var recordItem = recordBox.getChildByName('recordItem');
        if (recordItem) {
            recordItem.removeFromParent();
        }

        if (method === 'append') {
            this._createRecordItem_(dataArr, recordBox);
        } else {
            var dis = dataArr.length - recordBox.childrenCount;

            var itemsNode = recordBox.children;
            var len = Math.min(dataArr.length, recordBox.childrenCount);
            for (var i = 0; i < len; i++) {
                this._showOneItem_(dataArr[i], itemsNode[i]);
            }

            if (dis >= 0) {
                //如果节点不够，就创建节点
                this._createRecordItem_(dataArr.slice(i), recordBox);
            } else {
                //隐藏多余的节点
                for (var j = i; j < recordBox.childrenCount; j++) {
                    itemsNode[i].active = false;
                }
            }
        }

        // this.node.getChildByName('recordAlert').active = true; //显示 历史记录弹出框
    },

    /**
     * 创建节点并在这些节点上显示指定的数据
     * @private
     * @param {Array} dataArr -- 要显示的数据
     * @param {cc.Node} recordBox -- 要创建的节点的父节点
     */
    _createRecordItem_: function _createRecordItem_(dataArr, recordBox) {
        for (var i = 0; i < dataArr.length; i++) {
            var recordItem = cc.instantiate(this.recordItemPref); //创建节点
            this._showOneItem_(dataArr[i], recordItem, i);
            recordItem.name = 'recordItem' + i;
            recordItem.getChildByName('seeBack').on('click', this.node_seeMeVideo, this); //添加回放的点击事件
            recordItem.parent = recordBox;
        }
    },

    /**
     * 在一个节点上显示一条记录
     * @private
     * @param {object} obj -- 要显示的一条记录数据对象
     * @param {cc.Node} node -- 要显示数据的节点
     */
    _showOneItem_: function _showOneItem_(obj, node, i) {
        node.active = true; //显示节点
        cc.find('orderFrame/order', node).getComponent(cc.Label).string = i + 1; //设置序号
        cc.find('borderFrameRoom/roomId', node).getComponent(cc.Label).string = obj.roomId; //设置房间号
        node.getChildByName('time').getComponent(cc.Label).string = obj.time; //设置时间

        var scoresArr = node.getChildByName('users').children,
            nameNode,
            //存放名字节点
        nickName,
            //昵称
        score; //存放分数

        for (var i = 0; i < 4; i++) {
            nameNode = scoresArr[i];
            nickName = obj.usersInfo[i].nickName;
            nameNode.getComponent(cc.Label).string = nickName + ':'; //设置昵称
            score = obj.usersInfo[i].score;
            nameNode.getChildByName('score').getComponent(cc.Label).string = score > 0 ? '+' + score : score; //设置分数
        }
    },

    /**
     * 查看其它人的录像
     */
    node_seeOtherVideoClick: function node_seeOtherVideoClick(e) {
        console.log('点击了‘查看其它人的录像按钮’');
        window.audio.playEffect('waterClick'); //播放点击声音
    },

    /**
     * 回放按钮
     */
    node_seeMeVideo: function node_seeMeVideo(e) {
        var roomId = cc.find('borderFrameRoom/roomId', e.target.parent).getComponent(cc.Label).string;
        console.log('点击了‘回放按钮’' + roomId);
        window.audio.playEffect('waterClick'); //播放点击声音
    },

    /**
    * hall.sence场景 中的 Canvas节点 加载完成后 的回调
    * @callback
    */
    onLoad: function onLoad() {
        //存入全局中
        getGI('hall').recordAlert = this;
        // this._test_record_(); //(测试用)
    },

    //(测试用)
    _test_record_: function _test_record_() {
        var dataArr = [{ roomId: '6389292', time: '2016-11-29 14:03',
            usersInfo: [{ nickName: '如花', score: 118 }, { nickName: '如花', score: 78 }, { nickName: '如花', score: -28 }, { nickName: '如花', score: -28 }] }, { roomId: '6389293', time: '2016-14-29 14:05',
            usersInfo: [{ nickName: '如花1', score: -28 }, { nickName: '如花', score: 8 }, { nickName: '如花', score: 0 }, { nickName: '如花', score: -28 }] }, { roomId: '6389295', time: '2016-11-41 14:03',
            usersInfo: [{ nickName: '如花2', score: 458 }, { nickName: '如花', score: -28 }, { nickName: '如花', score: -28 }, { nickName: '如花', score: -28 }] }, { roomId: '6389232', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '如花3', score: -8 }, { nickName: '如花', score: -218 }, { nickName: '如花', score: 428 }, { nickName: '如花', score: -28 }] }, { roomId: '6389232', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '如花3', score: -8 }, { nickName: '如花', score: -218 }, { nickName: '如花', score: 428 }, { nickName: '如花', score: -28 }] }, { roomId: '6380032', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '如花3', score: -8 }, { nickName: '如花', score: -218 }, { nickName: '如花', score: 428 }, { nickName: '如花', score: -28 }] }, { roomId: '6384534', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '如花3', score: -8 }, { nickName: '如花', score: -218 }, { nickName: '如花', score: 428 }, { nickName: '如花', score: -28 }] }, { roomId: '6389238', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '如花3', score: -8 }, { nickName: '如花', score: -218 }, { nickName: '如花', score: 428 }, { nickName: '如花', score: -28 }] }, { roomId: '6459232', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '如花3', score: -8 }, { nickName: '如花', score: -218 }, { nickName: '如花', score: 428 }, { nickName: '如花', score: -28 }] }];
        this.showRecords(dataArr);
    }

});

cc._RFpop();
},{}],"node_settingAlert":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8bb11HNdNpE85xPtOsOWF4O', 'node_settingAlert');
// scripts\hall\node_settingAlert.js

'use strict';

/**
 * @class
 * @classdesc 本类挂载在 hall.sence场景中的 settingAlert节点 上
 */
cc.Class({
    extends: cc.Component,

    properties: {},

    /**
     * 设置弹出框界面中 音乐和音效的开关切换
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     */
    node_toggle: function node_toggle(event) {
        var toggleNode = event.target,
            isChecked = toggleNode.getComponent(cc.Toggle).isChecked;
        toggleNode.getChildByName('close').active = !isChecked;
        toggleNode.getChildByName('open').active = isChecked;

        //存入全局
        if (toggleNode.name.search('music') !== -1) {
            setGI('musicVolume', isChecked);
        } else {
            setGI('effectVolume', isChecked);
        }

        window.audio && window.audio.refreshVolume(); //刷新音频对象中的数值
    },

    /**
     * 设置弹出框界面中 普通话和闽南语的切换
     * @public
     * @param {Event} event -- 挂载到节点上时，自动传入的事件对象
     */
    node_langualeToggle: function node_langualeToggle(event) {
        var toggleNode = event.target.parent,
            isChecked = toggleNode.getComponent(cc.Toggle).isChecked;
        setGI('language', toggleNode.name);
        window.audio && window.audio.refreshLanguage(); //刷新音频对象中的数值
    },

    /**
     * 初始化设置
     * @private
     */
    _initSetting_: function _initSetting_() {
        var settingBg = cc.find('Canvas/settingAlert/alertContent/settingBg');
        var musicOpen = getGI('musicVolume');
        cc.find('music/musicToggle/close', settingBg).active = !musicOpen;
        cc.find('music/musicToggle/open', settingBg).active = musicOpen;
        cc.find('music/musicToggle', settingBg).getComponent(cc.Toggle).isChecked = musicOpen;

        var effectOpen = getGI('effectVolume');
        cc.find('effect/effectToggle/close', settingBg).active = !effectOpen;
        cc.find('effect/effectToggle/open', settingBg).active = effectOpen;
        cc.find('effect/effectToggle', settingBg).getComponent(cc.Toggle).isChecked = effectOpen;

        var puTongHuaActive, mingNanActive;
        if (getGI('language') === 'puTongHua') {
            puTongHuaActive = true;
            mingNanActive = false;
        } else {
            puTongHuaActive = false;
            mingNanActive = true;
        }
        cc.find('languageTG/puTongHuaLabel/puTongHua', settingBg).getComponent(cc.Toggle).isChecked = puTongHuaActive;
        cc.find('languageTG/mingNanLabel/mingNan', settingBg).getComponent(cc.Toggle).isChecked = mingNanActive;
    },

    /** 设置弹出框中的 解散房间按钮 点击时调用的函数
     * @param {Event} event --在页面中通过拖动添加事件后, 自动拥有这个参数，表示点击事件的event对象
     */
    node_dismissClick: function node_dismissClick(event) {
        window.audio.playEffect('waterClick'); //播放点击声音
        if (!room.attr('isCanDismiss')) {
            room.displayRoomInfo('onLine', '只有房间中的全部成员同时在线时，才能解散房间！');
            return;
        }
        this.node.active = false; //隐藏本弹出框
        room.dismissAlert.startDismiss(); //显示解散弹出框
    },

    /**
    * hall.sence场景中的 settingAlert节点 上 的回调
    * @callback
    */
    onLoad: function onLoad() {
        //存入全局中
        setGI('setAlert', this);

        this._initSetting_();
    }

});

cc._RFpop();
},{}],"operateCards_operate":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6dc1bgSfGFOZqVJbFCVyYmr', 'operateCards_operate');
// scripts\room\model\cardsMng\cardsOperate\operateCards_operate.js

'use strict';

/**
 * @class
 * @classdesc -- 主要管理碰、杠、吃的牌的操作
 */
var OperateCards_operate = cc.Class({
    name: 'OperateCards_operate',

    /**
     * @constructor
     * @param {Object} config -- {
     *                              operateCards: operateCards, //必须，操作的牌的管理对象
     *                              handCards  //必须，手中的牌的处理对象
     *                           }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.operateCards = config.operateCards; //操作的牌的管理对象
        this.handCards = config.handCards; //手中的牌的处理对象
    },

    /**
     * 获取操作的数组
     * @public
     */
    getCardsOperateArr: function getCardsOperateArr() {
        return this.operateCards.cardsOperateArr;
    },

    /**
     * 检测是否有某种类型的操作，有就返回true, 没有就返回false
     * @public
     * @param {string} type -- 必须，传入 'chi' | 'pong'等
     * @returns {boolean} -- 检测结果
     */
    isHasOperate: function isHasOperate(type) {
        var result = false;
        //elem = {c: 't3', t:'pong', from: '389292', i: 2, arr:[...] }
        this.operateCards.forEach(function (elem) {
            if (elem.t === type) {
                result = true;
                return 'break'; //退出循环
            }
        });
        return result;
    }

});

module.exports = OperateCards_operate;

cc._RFpop();
},{}],"operateCards":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a7d68oo+9xHiKphZ78iFsiE', 'operateCards');
// scripts\room\model\cardsMng\_cardsData_\operateCards.js

'use strict';

/**
 * @class
 * @classdesc -- 管理碰、杠、吃的牌
 */
var OperateCards = cc.Class({
    name: 'OperateCards',

    /**
     * @constructor
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.cardsOperateArr = []; //碰、杠、吃的牌
        /* this.cardsOperateArr =
        {p: '289383',  t: 'pong', c: 'w3', from: '3892938', m:['w3', 'w3'] }, //碰
        {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3', 'w3'] },//明杠
        {p: '289383',  t: 'anGang', c: 'w3', from: '3892938', m:['w3', 'w3', 'w3', 'w3'] },//暗杠
        {p: '289383',  t: 'buGang', c: 'w3', from: '3892938', m:['w3'] },//补杠, 与碰是分开的
        {p: '289383',  t: 'chi', c: 'w2', from: '3892938', m:['w1', 'w3', 'w1'] },//吃
        {p: '3889289', t: 'buHua', c:'', from: 'meOpenId', m:['i1', 'h3', 'i2']} //补花
          {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
        {p: 289383,  t: 'sanJingDao', c: 'w2', from: 3892938, m:[] }, //三金倒
        {p: 289383,  t: 'youJing', c: 'w2', from: 3892938, m:[] }, //游金
        {p: 289383,  t: 'shuangYou', c: 'w2', from: 3892938, m:[] }, //双游
        {p: 289383,  t: 'sanYou', c: 'w2', from: 3892938, m:[] }, //三游
        {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
        {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
        */
    },

    /**
     * 一次性设置多个操作
     * @public
     * @param {Array} cardsOperateArr -- 操作的数组
     */
    operateAttr: function operateAttr(name, value) {
        switch (name) {
            case 'cardsArr':
                if (value != undefined) {
                    this.cardsOperateArr = value;
                } else {
                    return this.cardsOperateArr;
                }
                break;
        }
    },

    /**
     * 每一局开始时重置对象中的参数
     * @public
    */
    reset: function reset() {
        console.log('operateCards.js重置了。。');
        this.cardsOperateArr.length = 0;
    },

    /**
     * 添加一个操作
     * @public
     * @param {object} operateObj -- {p: '289383',  t: 'chi', c: 'w2', from: '3892938', m:['w1', 'w3'] },//吃
     */
    addOperate: function addOperate(operateObj) {
        switch (operateObj.t) {
            case 'buGang':
                return this._buGang_(operateObj.c);
            case 'buHua':
                return this._buHua_(operateObj.c, operateObj);
            default:
                this.cardsOperateArr.push(operateObj);
        }
    },

    /**
     * 进行补杠(自己原来碰了，又摸到一张)
     * @private
     * @param {string} cardName -- 必须，要补扛的牌的名字 如: 'w2'
     *
     * @returns {number} -- 返回-1表示补扛失败，否则补扛成功，返回补扛的位置，取 0，1，2，3
     */
    _buGang_: function _buGang_(cardName) {
        var operateObj = void 0;
        for (var i = 0; i < this.cardsOperateArr.length; i++) {
            operateObj = this.cardsOperateArr[i];
            if (operateObj.t === 'pong' && operateObj.c === cardName) {
                operateObj.index = i;
                return operateObj;
            }
        }
        return operateObj;
    },

    /**
     * 进行补花
     * @private
     * @param {string} cardName -- 必须，要补花的牌的名字 如: 'i3'
     *
     * @returns {number} -- 返回-1表示补花失败，否则补花成功，返回花后的数组的长度
     */
    _buHua_: function _buHua_(cardName, operateObj1) {
        var operateObj;
        for (var i = 0; i < this.cardsOperateArr.length; i++) {
            operateObj = this.cardsOperateArr[i];
            if (operateObj.t === 'buHua') {
                operateObj.m.push(cardName);
                return operateObj;
            }
        }
        this.cardsOperateArr.push(operateObj1);
        return operateObj1;
    },

    /**
     * 用于升序顺序遍历所有的操作
     * @public
     * @param {function} callback -- 必须 callback.call(thisObj, elem, i , arr);
     *                              callback: 返回 'break' 表示退出整个循环，'continue' 表示continue整个循环
     * @param {this} thisObj --可省略 回调函数中this的指向，如果不传入本对像，就默认取 OperateCards 对像
    */
    forEach: function forEach(callback, thisObj) {
        thisObj = thisObj || this;

        var result,
            //传入回调函数的计算结果
        cardsOperateArr = this.cardsOperateArr; //操作的牌

        for (var i = 0; i < cardsOperateArr.length; i++) {
            result = callback.call(thisObj, cardsOperateArr[i], i, cardsOperateArr);
            switch (result) {
                case 'break':
                    //如果返回 break，就退出循环
                    break;
                case 'continue':
                    //如果返回 continue，就continue循环
                    continue;
            }
        }
    }

});

module.exports = OperateCards;

cc._RFpop();
},{}],"operate_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ccda8SjBLxKpYdlDaMd3LwK', 'operate_controller');
// scripts\common\controller\messageController\operate_controller.js

'use strict';

/**@class
 * @classdesc 用户在选项面板中点击操作成功后发的消息(也有可能不成功，如： 同时 有人碰有人胡)
 */
var Operate_controller = cc.Class({
    name: 'Operate_controller',

    /** 服务器端缓存消息:开始,出牌,拿牌/补花,吃/碰/明杠/补杠/暗杠,胡/自摸/三金倒,游金/双游/三游,单局结算/房间结算
     * 处理碰
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 52, c: 'w3', from: 3892938, m:['w3', 'w3'] }//碰的人的信息
                s | success: true, t | type: 52, pong
            }
     */
    pong_controller: function pong_controller(data) {
        console.log('客户端-- 执行 pong_controller...');

        this._processOperate_(data);

        socket.finishMsg('pong'); //标记本条消息处理完成
    },

    /**
     * 处理吃
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 13, c: 'w2', from: 3892938, m:['w3', 'w4'] } //暗杠人的信息
                s | success: true, t | type: 13, chi
            }
     */
    chi_controller: function chi_controller(data) {
        console.log('客户端-- 执行 chi_controller...');
        this._processOperate_(data);

        socket.finishMsg('chi'); //标记本条消息处理完成
    },

    /**
     * 处理明杠
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 52, c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] } //明杠人的信息
                s | success: true, t | type: 52, mingGang
            }
            message.p //明杠人的openId
     */
    mingGang_controller: function mingGang_controller(data) {
        console.log('客户端-- 执行 mingGang_controller...');

        this._processOperate_(data);

        //如果是客户杠的, 不用再显示
        if (room.msgIsMeSend(data.message.p)) {
            clientUser.send({ type: 'napai' }); //发送拿牌消息
        }

        socket.finishMsg('mingGang'); //标记本条消息处理完成
    },

    /**
     * 处理补杠
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 13, c: 'w3', from: 3892938, m:['w3'] } //补杠人的信息
                s | success: true, t | type: 51, buGang
            }
     */
    buGang_controller: function buGang_controller(data) {
        console.log('客户端-- 执行 buGang_controller...');

        this._processOperate_(data);

        //如果是客户杠的, 不用再显示
        if (room.msgIsMeSend(data.message.p)) {
            clientUser.send({ type: 'napai' }); //发送拿牌消息
        }

        socket.finishMsg('buGang'); //标记本条消息处理完成
    },

    /**
     * 处理暗杠
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 51, c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] } //暗杠人的信息
                s | success: true, t | type: 51, buGang
            }
     */
    anGang_controller: function anGang_controller(data) {
        console.log('客户端-- 执行 anGang_controller...');

        //如果是客户杠的, 不用再显示
        if (room.msgIsMeSend(data.message.p)) {
            // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息
            clientUser.send({ type: 'napai' }); //发送拿牌消息
            socket.finishMsg('anGang'); //标记本条消息处理完成
            return;
        }

        this._processOperate_(data);

        socket.finishMsg('anGang'); //标记本条消息处理完成
    },

    /**
     * 处理 【 明杠、暗杠、碰、吃、补杠 】
     * @private
     * @param {object} data -- 收到的消息
     */
    _processOperate_: function _processOperate_(data) {
        // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息

        var operateMsg = window.TOOL.converOperate(data.message); // 将t=52 -> t='mingGang'

        var userId = operateMsg.p,
            //明杠人的userId
        user = room.getUser(userId),
            //用户
        next = room.getNextActiveIndex(userId); //按顺时针算，下一个用户的位置号

        user.operateItem(operateMsg);
        console.log('operate: 下一当前用户是： ' + next);

        room.setActiveUser(next); //设置下一个用户
        room.displayPointer(next); //指向当前用户并倒计时

        clientUser.setAttr('isCanOperate');
    },

    // /**
    //  * 【游金、双游、三游】，都相当于出一张牌
    //  * @private
    //  */
    // _youJingProcess_(data){
    //     // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息
    //     TOOL.converOperate(data.message); //进行消息中的 t字段的转换
    //     var cardName = data.message.m[0];
    //     var userId = data.message.p;

    //     // 如果不是自己发的消息才处理, 因为自己的出牌已经在点击出牌时已经处理过了
    //     if( !room.msgIsMeSend(userId) ){
    //         //取得出牌的用户
    //             var user = room.getUser(userId);

    //             if(!data.e){ //如果自己没有操作
    //                 clientUser.send({type: 'guo'});
    //             }else{
    //                 var optArr = data.e;
    //                 if(optArr && optArr.length>0){ //如果有操作, 客户要显示出来
    //                     clientUser.attr('operateArr', optArr);
    //                     clientUser.mahjongMng.showPanel( TOOL.converOperate(optArr) );
    //                 }
    //             }
    //             user.outCard(cardName, userId);
    //             user.displayHead('youJing', data.message.t + 'Zhong'); //显示它人正在游金中
    //     }
    // },

    /**
     * 处理 游金
     * data: {  e | errMsg: null,
                m | message: {p: 289383,  t: 'youJing', c: '', from: 3892938, m:['w2', 't4'] }, //游金
                s | success: true, t | type: 51, buGang
            }
     */
    youJing_controller: function youJing_controller(data) {
        console.log('客户端-- 执行 youJing_controller...');
        this._huProcess_(data); //相当于出一张牌
        socket.finishMsg('youJing'); //标记本条消息处理完成
    },
    /**
     * 处理 双游
     * data: {  e | errMsg: null,
                m | message: {p: 289383,  t: 'shuangYou', c: '', from: 3892938, m:['w2', 't4'] }, //双游
                s | success: true, t | type: 51, buGang
            }
     */
    shuangYou_controller: function shuangYou_controller(data) {
        console.log('客户端-- 执行 shuangYou_controller...');
        this._huProcess_(data); //相当于出一张牌
        socket.finishMsg('shuangYou'); //标记本条消息处理完成
    },
    /**
     * 处理 三游
     * data: {  e | errMsg: null,
                m | message: {p: 289383,  t: 'sanYou', c: '', from: 3892938, m:['w2', 't4'] }, //三游
                s | success: true, t | type: 51, buGang
            }
     */
    sanYou_controller: function sanYou_controller(data) {
        console.log('客户端-- 执行 sanYou_controller...');
        this._huProcess_(data); //相当于出一张牌
        socket.finishMsg('sanYou'); //标记本条消息处理完成
    },

    /**
     * 【三金倒、自摸、天胡、点炮胡】，都相当于出一张牌
     * @private
     */
    _huProcess_: function _huProcess_(data) {
        // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息
        if (room.msgIsMeSend(data.message.p)) {
            //只有胡牌的那个人才发消息
            clientUser.send({ type: 'juScore', message: data.message });
        }
    },

    /**
     * 处理 三金倒
     */
    sanJingDao_controller: function sanJingDao_controller(data) {
        console.log('客户端-- 执行 sanJingDao_controller...');
        this._huProcess_(data);
        socket.finishMsg('sanJingDao'); //标记本条消息处理完成
    },

    /**
     * 处理 自摸
     */
    ziMo_controller: function ziMo_controller(data) {
        console.log('客户端-- 执行 ziMo_controller...');
        this._huProcess_(data);
        socket.finishMsg('ziMo'); //标记本条消息处理完成
    },
    /**
     * 处理 天胡
     */
    tianHu_controller: function tianHu_controller(data) {
        console.log('客户端-- 执行 tianHu_controller...');
        this._huProcess_(data);
        socket.finishMsg('tianHu'); //标记本条消息处理完成
    },

    /**
     * 处理 点炮胡
     * data: {  e | errMsg: null,
                m | message: {t:8, c: 'w2', from: '384827', 'p': '10194'} //胡牌人的信息
                s | success: true, t | type: 8, hu
            }
    */
    hu_controller: function hu_controller(data) {
        console.log('客户端-- 执行 hu_controller...');
        this._huProcess_(data);
        socket.finishMsg('hu'); //标记本条消息处理完成
    }

});

module.exports = Operate_controller;

cc._RFpop();
},{}],"operate_serverController":[function(require,module,exports){
"use strict";
cc._RFpush(module, '500c9jk/2RF75rN0pMsef2R', 'operate_serverController');
// scripts\server\serverController\operate_serverController.js

'use strict';

/**@class
 * @classdesc 用户在选项面板中点击操作成功后发的消息(也有可能不成功，如： 同时 有人碰有人胡)
 */
var Operate_serverController = cc.Class({
    name: 'Operate_serverController',

    /**
     * 处理碰
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 52, c: 'w3', from: 3892938, m:['w3', 'w3'] }//碰的人的信息
                s | success: true, t | type: 52, pong
            }
     */
    pong_controller: function pong_controller(data) {
        console.log('服务器-- 执行 pong_controller...');
        localServer.send({ type: window.messageType[data.message.t], message: data.message });
    },

    // /**
    //  * 一个人出牌后，其它几个人对这张牌有操作，但都选的是过，则每个人都会收到一条过的消息
    //  */
    // guo_controller: function(data){
    //     console.log('执行 guo_controller...');
    //     var next = room.getNextActiveIndex();  //按顺时针算，下一个用户的位置号

    //     console.log('operate: 下一当前用户是： ' + next);

    //     room.setActiveUser(next); //设置下一个用户
    //     room.displayPointer(next); //指向当前用户并倒计时

    //     if( next === 0){ //下一个是自己
    //         clientUser.send( {type: 'napai'} );  //发送拿牌消息
    //     }

    //     socket.finishMsg('guo'); //标记本条消息处理完成
    // },

    /**
     * 处理明杠
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 52, c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] } //明杠人的信息
                s | success: true, t | type: 52, mingGang
            }
            message.p //明杠人的openId
     */
    mingGang_controller: function mingGang_controller(data) {
        console.log('服务器-- 执行 mingGang_controller...');
        localServer.send({ type: window.messageType[data.message.t], message: data.message });
    },

    /**
     * 处理吃
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 13, c: 'w2', from: 3892938, m:['w3', 'w4'] } //暗杠人的信息
                s | success: true, t | type: 13, chi
            }
     */
    chi_controller: function chi_controller(data) {
        console.log('服务器-- 执行 chi_controller...');
        localServer.send({ type: window.messageType[data.message.t], message: data.message });
    },

    /**
     * 处理补杠
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 13, c: 'w3', from: 3892938, m:['w3'] } //补杠人的信息
                s | success: true, t | type: 51, buGang
            }
     */
    buGang_controller: function buGang_controller(data) {
        console.log('服务器-- 执行 buGang_controller...');
        localServer.send({ type: window.messageType[data.message.t], message: data.message });
    },

    /**
     * 处理暗杠
     * data: {  e | errMsg: null,
                m | message: {p: '289383',  t: 51, c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] } //暗杠人的信息
                s | success: true, t | type: 51, buGang
            }
     */
    anGang_controller: function anGang_controller(data) {
        console.log('服务器-- 执行 anGang_controller...');
        localServer.send({ type: window.messageType[data.message.t], message: data.message });
    },

    /**
     * 胡
     * data: {  e | errMsg: null,
                m | message: {t:8, c: 'w2', from: '384827', 'p': '10194'} //胡牌人的信息
                s | success: true, t | type: 8, hu
            }
    */
    hu_controller: function hu_controller(data) {
        console.log('服务器-- 执行 hu_controller...');
        // // debugger;
        // //var operateMsg =  window.messageType.convert(data.message, 'send_big');  //{ type: 'hu', message: '{huCard: 'w2',from: '384827'}', openId: '10114'}

        // var operateMsg = {};
        // operateMsg.operateName = 'hu';
        // operateMsg.huCard = data.message.c;
        // operateMsg.from = data.message.from;
        // operateMsg.openId = data.message.p;

        // var isFinished = true; //是否已经将胡的消息接收完了

        // //如果是客户自己发的消息, 并且是自摸的
        // if( room.msgIsMeSend(operateMsg.openId) && operateMsg.openId === operateMsg.from ){

        // }else{
        //     var user = room.getUser(operateMsg.openId);
        //     user.operateItem( {operateName:'hu', operateCard: operateMsg.huCard, from: operateMsg.from} );
        //     var next = room.getNextActiveIndex( operateMsg.openId );  //按顺时针算，下一个用户的位置号

        //     console.log('operate: 下一当前用户是： ' + next);

        //     room.setActiveUser(next); //设置下一个用户
        //     room.displayPointer(next); //指向当前用户并倒计时
        // }

        // //如果将所有胡的消息收完了，就开始处理
        // if(isFinished){
        //    var resultData =  { type: 'hu', message: {huCard: operateMsg.huCard ,from: operateMsg.from}, openId: operateMsg.openId}
        //    clientUser.juEnd(resultData);
        //    clientUser.attr('isReady', false);
        // }

        socket.finishMsg('hu'); //标记本条消息处理完成
    }

});

module.exports = Operate_serverController;

cc._RFpop();
},{}],"outCardMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '3aa4eZMHdxBuqM7r1pVdTMW', 'outCardMng');
// scripts\room\view\userLayer\display\outCardMng.js

'use strict';

/**@class
 * @classdesc 管理出牌的类
 */
var OutCardMng = cc.Class({
    name: 'OutCardMng',
    /**
     * @constructor
     * @param {Object} userNode
     */
    ctor: function ctor() {
        this.userNode = arguments[0]; //用户自己的节点
        this.mj_Atlas = getGI('roomGlobalRes').mj_Atlas; //麻将牌的图集
        this.outNodes = this.userNode.getChildByName('out'); //出牌的节点对象
        this.reset();
    },

    /**
     * 当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        //当新一局开始时要重置
        console.log('outCardMng.js重置了。');
        this.outPointerIndex = -1; //指向哪一个节点，-1表示一张牌也没显示, 0表示出了一张牌
        this.outLineIndex = 0; //已经显示到第几行

        this._hideAllNodes_(this.outNodes); //隐藏第一排出的牌
    },

    /**
     * 隐藏一个父节点下的所有子节点
     * @private
     * @param {cc.Node} parentNode -- 传入一个父节点
     */
    _hideAllNodes_: function _hideAllNodes_(parentNode) {
        var children = parentNode.children;
        for (var i = 0; i < children.length; i++) {
            children[i].active = false;
        }
    },

    /**
     * 计算出牌的位置
     * @public
     * @param {string} direction -- 前进的方向，'forward' 表示出一张牌， 'backward'表示别人碰了后，要后退一张牌
     * @param {string} cardName -- 要显示的牌的名字，当direction === 'forward' 时，必须传入
    */
    operateOutCard: function operateOutCard(direction, cardName) {
        var outNode;
        switch (direction) {
            case 'forward':
                this._nextPointer_(); //计算下一人位置
                outNode = this.outNodes.getChildByName('mj' + this.outLineIndex + this.outPointerIndex);
                outNode.getChildByName('hua').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardName); //更改牌
                break;
            case 'backward':
                outNode = this.outNodes.getChildByName('mj' + this.outLineIndex + this.outPointerIndex);
                this._prevPointer_(); //计算下一人位置
                break;
        }
        return outNode;
    },

    /**
     * 第一排7张，第二排9张，第三排12张
     * 计算下一个位置
     * @private
     */
    _nextPointer_: function _nextPointer_() {
        this.outPointerIndex++;
        if (this.outLineIndex === 0 && this.outPointerIndex > 6) {
            this.outLineIndex = 1;this.outPointerIndex = 0;
        } else if (this.outLineIndex === 1 && this.outPointerIndex > 8) {
            this.outLineIndex = 2;this.outPointerIndex = 0;
        }
    },

    /**
     * 第一排7张，第二排9张，第三排12张
     * 计算上一个位置
     * @private
     */
    _prevPointer_: function _prevPointer_() {
        this.outPointerIndex--;
        if (this.outPointerIndex < 0) {
            if (this.outLineIndex === 0) {
                this.outPointerIndex = 0;
            } else if (this.outLineIndex === 1) {
                this.outLineIndex = 0;this.outPointerIndex = 6;
            } else if (this.outLineIndex === 2) {
                this.outLineIndex = 1;this.outPointerIndex = 8;
            }
        }
    },

    /**
     * 一次性显示很多张出的牌
     * @public
     * @param {Array} cardsArr -- 牌的名字的一维数组 如： ['w3', 't6', 'f2']
     */
    operateAllOutCard: function operateAllOutCard(cardsArr) {
        var outNode;
        var cardsLen = cardsArr.length; //牌的长度

        for (var i = 0; i < cardsLen; i++) {
            outNode = this.operateOutCard('forward', cardsArr[i]);
            outNode.active = true; //显示节点
        }
    }

});

module.exports = OutCardMng;

cc._RFpop();
},{}],"outCard_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'fe9c3vqeS9CILRqnRmDC8c7', 'outCard_controller');
// scripts\common\controller\messageController\outCard_controller.js

'use strict';

/**@class
 * @classdesc 一局或八局结束
 */
var OutCard_controller = cc.Class({
    name: 'OutCard_controller',

    /**
     * 已收到别人出的牌后，自己对这张牌进行处理
     * data: {  e | errMsg: null,
                m | message: {t:7, m: 'w3', 'p': '10114'} //出牌人的信息
                s | success: true, t | type: 7, outCard
            }
     */
    outCard_controller: function outCard_controller(data) {
        console.log('客户端-- 执行 outCard_controller...');
        //    clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息

        this._processOutCard_(data);

        socket.finishMsg('outCard'); //标记本条消息处理完成
    },

    _processOutCard_: function _processOutCard_(data) {
        var cardName = data.message.m;
        var userId = data.message.p;

        // 如果不是自己发的消息才处理, 因为自己的出牌已经在点击出牌时已经处理过了
        if (!room.msgIsMeSend(userId)) {
            //取得出牌的用户
            var user = room.getUser(userId);

            if (!data.e) {
                //如果自己没有操作
                clientUser.send({ type: 'guo' });
            } else {
                var optArr = data.e;
                if (optArr && optArr.length > 0) {
                    //如果有操作, 客户要显示出来
                    clientUser.attr('operateArr', optArr);
                    clientUser.mahjongMng.showPanel(TOOL.converOperate(optArr));
                }
            }
            user.outCard(cardName, userId);
        }
    },

    _processYouJingCard_: function _processYouJingCard_(data) {
        var cardName = data.message.m;
        var userId = data.message.p;

        // 如果不是自己发的消息才处理, 因为自己的出牌已经在点击出牌时已经处理过了
        if (!room.msgIsMeSend(userId)) {
            //取得出牌的用户
            var user = room.getUser(userId);

            user.displayHead('youJing', data.type); //在用户头像上显示正在游金中

            if (!data.e) {
                //如果自己没有操作
                clientUser.send({ type: 'guo' });
            } else {
                var optArr = data.e;
                if (optArr && optArr.length > 0) {
                    //如果有操作, 客户要显示出来
                    clientUser.attr('operateArr', optArr);
                    clientUser.mahjongMng.showPanel(TOOL.converOperate(optArr));
                }
            }
            user.outCard(cardName, userId);
        }
    },

    /**
     * 游金中， 与出牌消息一样，只是类型不同
     * data: {  e | errMsg: null,
                m | message: {t:7, m: 'w3', 'p': '10114'} //出牌人的信息
                s | success: true, t | type: 7, outCard
            }
     */
    youJingZhong_controller: function youJingZhong_controller(data) {
        console.log('客户端-- 执行 youJingZhong_controller...');
        this._processYouJingCard_(data);
        socket.finishMsg('youJingZhong'); //标记本条消息处理完成
    },

    /**
     * 游金中， 与出牌消息一样，只是类型不同
     * data: {  e | errMsg: null,
                m | message: {t:7, m: 'w3', 'p': '10114'} //出牌人的信息
                s | success: true, t | type: 7, outCard
            }
     */
    shuangYouZhong_controller: function shuangYouZhong_controller(data) {
        console.log('客户端-- 执行 shuangYouZhong_controller...');
        this._processYouJingCard_(data);
        socket.finishMsg('shuangYouZhong'); //标记本条消息处理完成
    },

    /**
     * 游金中， 与出牌消息一样，只是类型不同
     * data: {  e | errMsg: null,
                m | message: {t:7, m: 'w3', 'p': '10114'} //出牌人的信息
                s | success: true, t | type: 7, outCard
            }
     */
    sanYouZhong_controller: function sanYouZhong_controller(data) {
        console.log('客户端-- 执行 sanYouZhong_controller...');
        this._processYouJingCard_(data);
        socket.finishMsg('sanYouZhong'); //标记本条消息处理完成
    }
});

module.exports = OutCard_controller;

cc._RFpop();
},{}],"outCards_operate":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'eca34mVeMNCfLy56x7kbD3w', 'outCards_operate');
// scripts\room\model\cardsMng\cardsOperate\outCards_operate.js

'use strict';

/**
 * @class
 * @classdesc -- 管理自己出的牌
 */
var OutCards_operate = cc.Class({
    name: 'OutCards_operate',
    /**
     * @constructor
     * @param {object} outCards -- 出牌的管理对象
     */
    ctor: function ctor() {
        this.outCards = arguments[0];
    },

    /**
     * 已经出了多少张牌，注意，如果他出了牌，然后被别人碰了，也算出了一张牌
     * @returns {number} -- 出的牌的张数
     */
    getOutCardNum: function getOutCardNum() {
        return this.outCards.originalCardsOut.length;
    }
});

module.exports = OutCards_operate;

cc._RFpop();
},{}],"outCards":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd97b6rK5KFFArh5NkAOO3wd', 'outCards');
// scripts\room\model\cardsMng\_cardsData_\outCards.js

'use strict';

/**
 * @class
 * @classdesc -- 管理自己出的牌
 */
var OutCards = cc.Class({
    name: 'OutCards',

    ctor: function ctor() {
        this.originalCardsOut = []; //出的牌 形如：['w2', 't8', 'w4', 't2', 'w9'], 但只能进不能出(当别人碰了后，不会变)
        this.cardsOut = []; //出的牌 形如：['w2', 't8', 'w4', 't2', 'w9']， 可进可出(当别人碰了后，就会少一张), 用于显示时，用于显示
    },

    outArr: function outArr(name, value) {
        var arr = value;
        switch (name) {
            case 'cardsArrOgn':
                if (value != undefined) {
                    this.originalCardsOut.length = 0;
                    for (var i = 0; i < arr.length; i++) {
                        this.originalCardsOut.push(arr[i]);
                    }
                } else {
                    return this.originalCardsOut;
                }
                break;
            case 'cardsArrN':
                if (value != undefined) {
                    this.cardsOut.length = 0;
                    for (var i = 0; i < arr.length; i++) {
                        this.cardsOut.push(arr[i]);
                    }
                } else {
                    return this.cardsOut;
                }
                break;
            case 'cardsArrTwo':
                if (value != undefined) {
                    this.originalCardsOut.length = 0;
                    this.cardsOut.length = 0;
                    for (var i = 0; i < arr.length; i++) {
                        this.cardsOut.push(arr[i]);
                        this.originalCardsOut.push(arr[i]);
                    }
                } else {
                    return this.cardsOut;
                }
                break;
        }
    },

    /**
     * 每一局开始时重置对象中的参数
     * @public
    */
    reset: function reset() {
        this.cardsOut.length = 0; //清空出的牌的数组
        this.originalCardsOut.length = 0; //清空出的牌的数组
    },

    /**
     * 向出牌数组中添加一张牌
     * @public
     * @param {string} cardName -- 要出的牌的名字 如: 'w2'
     * @returns {number} -- 数组新的长度
     */
    pushCard: function pushCard(cardName) {
        this.originalCardsOut.push(cardName);
        return this.cardsOut.push(cardName);
    },

    /**
     * 从出牌数组中减去最近的一张牌
     * @public
     * @returns {string} -- 减去的牌的名字
     */
    popCard: function popCard() {
        return this.cardsOut.pop();
    }
});

module.exports = OutCards;

cc._RFpop();
},{}],"pageLog":[function(require,module,exports){
"use strict";
cc._RFpush(module, '5daefaMTyJArInWySn+X+AU', 'pageLog');
// scripts\common\pageLog.js

'use strict';

/**
 * 页面调试信息的输出
 */
var pageLog = {

    /**
     * 输出调试信息
     * @public
     */
    log: function log(str1, str2 /*...*/) {
        if (getGI('debug') === 0) {
            return;
        }
        var args = arguments;

        if (!window.logPref) {
            var self = this;
            clearInterval(this.timer);
            this.timer = setInterval(function () {
                if (window.logPref) {
                    clearInterval(self.timer);
                    self.timer = null;
                    self._log_(args);
                }
            }, 300);
        } else {
            this._log_(args);
        }
    },

    /**
     * 输出页面日志
     */
    _log_: function _log_(arr) {
        for (var i = 0; i < arr.length; i++) {
            var logbar = cc.instantiate(window.logPref);
            logbar.name = 'logbar';
            logbar.getComponent(cc.Label).string = arr[i];
            logbar.parent = cc.find('Canvas/pageLog/log/view/content');
        }
    },

    /**
     * 控制调试按钮是否显示
     * @public
     */
    generateLogNode: function generateLogNode() {
        if (getGI('debug') === 1) {
            var self = this;
            if (!this.pageLogPref) {
                cc.loader.loadRes("prefabs/pageLog", function (err, pageLogPref) {
                    self.pageLogPref = pageLogPref;
                    self._generateNode_(pageLogPref);
                });
            } else {
                this._generateNode_(this.pageLogPref);
            }

            if (!window.logPref) {
                cc.loader.loadRes("prefabs/logbar", function (err, logPref) {
                    window.logPref = logPref;
                });
            }
        }
    },

    /**
     * 生成调试节点
     * @private
     */
    _generateNode_: function _generateNode_(pageLogPref) {
        var pageLog = cc.instantiate(pageLogPref);
        var logBtn = pageLog.getChildByName('logBtn');
        logBtn.active = true;

        logBtn.on('click', function () {
            var logNode = pageLog.getChildByName('log');
            var isActive = !logNode.active;
            logNode.active = isActive;
            pageLog.getChildByName('bg').active = isActive;
        });

        //清空以前输出的内容
        cc.find('log/clearBtn', pageLog).on('click', function () {
            cc.find('log/view/content', pageLog).removeAllChildren(); //删除所有子节点
        });

        this._addLogClick_(pageLog);
        pageLog.parent = cc.find('Canvas');
    },

    /**
     * 调试时，为调试按钮添加点击事件
     * @private
     */
    _addLogClick_: function _addLogClick_(pageLog) {
        var time,
            clickNum = 0;
        var logNode = pageLog.getChildByName('log');
        var bg = pageLog.getChildByName('bg');

        function doubleClick() {
            clickNum++;
            if (clickNum === 1) {
                time = new Date().getTime();
            } else if (clickNum === 2) {
                if (new Date().getTime() - time < 200) {
                    logNode.active = false;
                    bg.active = false;
                }
                clickNum = 0;
            }
        };

        if ('touches' in cc.sys.capabilities) {
            logNode.on('touchend', doubleClick, this);
        } else if ('mouse' in cc.sys.capabilities) {
            logNode.on('mouseup', doubleClick, this);
        }
    }
};

module.exports = pageLog;

cc._RFpop();
},{}],"rebuildMsgMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a3e19FDoZtBrrgwUyJh1NrQ', 'rebuildMsgMng');
// scripts\server\rebuildMsgMng.js

'use strict';

var rebuild = {
    /**
     * 重新构建场景的数据
     *
     * data: {  e | errMsg: null, s | success: true, t | type: 35, rebuild
                m | message: {
                    meIndex: 0, //（这个是自己加上去的）客户在players数组中的位置
                    msg: null, //如果没有附加消息，就是null,否则就是要处理的消息
                    room: {
                        roomId: 8002, //房间号,
                        creator: 38929, //房主
                        totalJuNum: '', //没用
                        totalQuanNum: 1, //总局数, 8局--1 16局--2， 分数打法：100分--3 200分--4
                        opt: [1,2], //opt[0]: 2分--1 4分--2 8分--3
                                    //opt[1]: 单游不能平到--1 双游不能平到--2
                          useJuNum: 2,  //当前局数,
                        activer: 2, //活动用户的顺序号，取值：1,2,3,4
                          //1-创建或加入房间(刚进入房间, 还没发牌) 2-开始打牌(已经发牌了--某个人胡牌之前)
                        //3-单局结束(某个人胡了后--下一局发牌之前) 4-申请解散的过程中
                        step: 1,
                        status: 0, //当前状态,出牌2,拿牌1,闲置0
                    },
                    players: [playInfo1, playInfo2, playInfo3, playInfo4], //四个用户的详细信息
                    p: 389293, //自己的userId
                },
            }
          playInfo1 = {
            outAllCards: ['w3', 't6', 'b', 't3'], //所有出的牌
            outCards: ['w3', 't6', 'b', 't3'] , //剩余出的牌(被别人碰了后，自己出的牌会少一张)
            handCards: ['w3', 't6', 'w6', 'o8'], //手牌
            optMsgs: [...], //操作类型,吃碰杠等的数据
            huaCards: [...], //补了的花
            order: 1, //加入房间的顺序，取值：1,2,3,4
            userInfo: { //用户信息
                ready: true, //是否准备好了
                cards: 1000, sex: 1, ip: '192.168.0.196', faceIcon: "5350b36d56b6e054.jpg", isVip: true,
                nickName: "玩家10003", openId: "10003", userId: 10004, state: 0, //0-离线 1-在线
            },
              currentScore: 39, //上局得分
            roomScore: 99, //自己的总分
            napai: '', //可能分别为 'w5' null, 但是每个人都能看到 'w5'
        }
          msg =  {  e : null, s : true, t: 7,
            m : {t:7, m: 'w3', 'p': '10114'} ,//出牌人的信息
        };
          playInfo1.cardsShow =
             [
                {p: 289383,  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
                {p: 289383,  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] },//明杠
                {p: 289383,  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] },//暗杠
                {p: 289383,  t: 'buGang', c: 'w3', from: 3892938, m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
                {p: 289383,  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] },//吃
                {p: 3889289, t: 'buHua', c:'', from: meUserId, m:['i1', 'h3', 'i2']} //补花合在 specialGang中了
            ]
     */

    //发送重构消息
    sendRebuildMsg: function sendRebuildMsg() {
        localServer.send({ type: 'rebuild', message: this._getMessageData_() });
    },

    //获取应该发给客户的完整的消息体
    _getMessageData_: function _getMessageData_() {
        var message = {};
        message.msg = null; //如果没有附加消息，就是null,否则就是要处理的消息
        // temp.msg =  {  e : null, s : true, t: 7,
        //     m : {t:7, m: 'w3', 'p': '10114'} ,//出牌人的信息
        // };
        message.room = this._getRoomData_();
        message.players = this._getPlayers_();
        message.p = window.testData.test_users[0].userId; //自己的userId

        return message;
    },

    //生成房间信息
    _getRoomData_: function _getRoomData_() {
        var roomData = {
            roomId: 8002, //房间号,
            creator: 38929, //房主
            totalJuNum: '', //没用
            totalQuanNum: 1, //总局数, 8局--1 16局--2， 分数打法：100分--3 200分--4
            opt: [1, 2], //opt[0]: 2分--1 4分--2 8分--3
            //opt[1]: 单游不能平到--1 双游不能平到--2

            useJuNum: 2, //当前局数,
            activer: 2, //活动用户的顺序号，取值：1,2,3,4

            //1-创建或加入房间(刚进入房间, 还没发牌) 2-开始打牌(已经发牌了--某个人胡牌之前)
            //3-单局结束(某个人胡了后--下一局发牌之前) 4-申请解散的过程中
            step: 2,
            status: 0 };
        return roomData;
    },

    /**
      //其中一个用户对象的信息如下：
       oneUser = {
               outAllCards: ['w3', 't6', 'b', 't3'], //所有出的牌
               outCards: ['w3', 't6', 'b', 't3'] , //剩余出的牌(被别人碰了后，自己出的牌会少一张)
               handCards: ['w3', 't6', 'w6', 'o8'], //手牌
               cardsShow: [...], //操作类型,吃碰杠等的数据
               order: 1, //加入房间的顺序，取值：1,2,3,4
               userInfo: { //用户信息
                   ready: true, //是否准备好了
                   cards: 1000, sex: 1, ip: '192.168.0.196', faceIcon: "5350b36d56b6e054.jpg",
                   nickName: "玩家10003", openId: "10003", userId: 10004, state: 0, //0-离线 1-在线
               },
                 currentScore: 39, //上局得分
               roomScore: 99, //自己的总分
               napai: '', //可能分别为 'w5' null, 但是每个人都能看到 'w5'
           },
       */
    //获取所有用户信息
    _getPlayers_: function _getPlayers_() {
        var players = [];

        window.serverData.roomUsers = [];

        for (var i = 0; i < 4; i++) {
            var temp = {};
            var cardsData = window.testData.test_cardsData[i];

            temp.outAllCards = cardsData.outAllCards; //所有出的牌
            temp.outCards = cardsData.outCards; //剩余出的牌(被别人碰了后，自己出的牌会少一张)
            temp.handCards = cardsData.handCards; //手牌
            temp.cardsShow = cardsData.optMsgs; //所有吃碰杠的牌
            temp.order = i + 1; //加入房间的顺序，取值：1,2,3,4
            temp.userInfo = window.testData.test_users[i]; //用户信息

            window.serverData.roomUsers.push(temp.userInfo);

            temp.currentScore = this._getRandom_(-40, 40); //上局得分
            temp.roomScore = this._getRandom_(-150, 150); //自己的总分
            temp.napai = 'w7'; //可能分别为 'w5' null, 但是每个人都能看到 'w5'

            players.push(temp);
        }

        return players;
    },

    //产生一个 [min, max] 范围的随机整数
    _getRandom_: function _getRandom_(min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }

};

module.exports = rebuild;

cc._RFpop();
},{}],"rebuild_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '83ac7gCz2FHyayQAj6n+1bC', 'rebuild_controller');
// scripts\common\controller\messageController\rebuild_controller.js

'use strict';

var RebuildMsg = require('rebuild_msg_controller');
var msgStartRun = require('msgStartRun');

var msgComm = require('msgComm');

/**@class
 * @classdesc 一局或八局结束
 */
var Rebuild_controller = cc.Class({
    name: 'Rebuild_controller',

    /**
     * 重新构建场景
     *
     * data: {  e | errMsg: null, s | success: true, t | type: 35, rebuild
                m | message: {
                    meIndex: 0, //（这个是自己加上去的）客户在players数组中的位置
                    msg: null, //如果没有附加消息，就是null,否则就是要处理的消息
                    room: {
                        roomId: 8002, //房间号,
                        zhuangId: '389928', //庄家的id, 注意，这里传的是字符串
                        creator: '38929', //房主
                        totalJuNum: '', //没用
                        totalQuanNum: 1, //总局数, 8局--1 16局--2， 分数打法：100分--3 200分--4
                        opt: [1,2], //opt[0]: 2分--1 4分--2 8分--3
                                    //opt[1]: 单游不能平到--1 双游不能平到--2
                          useJuNum: 2,  //当前局数,
                        activer: 2, //活动用户的顺序号，取值：1,2,3,4
                          //1-创建或加入房间(刚进入房间, 还没发牌) 2-开始打牌(已经发牌了--某个人胡牌之前)
                        //3-单局结束(某个人胡了后--下一局发牌之前) 4-申请解散的过程中
                        step: 1,
                        status: 0, //当前状态,出牌2,拿牌1,闲置0
                        goldCard: 'w3', //金牌
                    },
                    players: [playInfo1, playInfo2, playInfo3, playInfo4], //四个用户的详细信息
                    p: 389293, //自己的userId
                },
            }
          playInfo1 = {
            outAllCards: ['w3', 't6', 'b', 't3'], //所有出的牌
            outCards: ['w3', 't6', 'b', 't3'] , //剩余出的牌(被别人碰了后，自己出的牌会少一张)
            handCards: ['w3', 't6', 'w6', 'o8'], //手牌
            optMsgs: [...], //操作类型,吃碰杠等的数据
            huaCards: [...], //补了的花
            order: 1, //加入房间的顺序，取值：1,2,3,4
            userInfo: { //用户信息
                ready: true, //是否准备好了
                cards: 1000, sex: 1, ip: '192.168.0.196', faceIcon: "5350b36d56b6e054.jpg", isVip: true,
                nickName: "玩家10003", openId: "10003", userId: 10004, state: 0, //0-离线 1-在线
            },
              currentScore: 39, //上局得分
            roomScore: 99, //自己的总分
            napai: '', //可能分别为 'w5' null, 但是每个人都能看到 'w5'
        }
          msg =  {  e : null, s : true, t: 7,
            m : {t:7, m: 'w3', 'p': '10114'} ,//出牌人的信息
        };
          playInfo1.optMsgs =
             [
                {p: 289383,  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
                {p: 289383,  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] },//明杠
                {p: 289383,  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] },//暗杠
                {p: 289383,  t: 'buGang', c: 'w3', from: 3892938, m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
                {p: 289383,  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] },//吃
                  {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
                {p: 289383,  t: 'sanJingDao', c: 'w2', from: 3892938, m:[] }, //三金倒
                {p: 289383,  t: 'youJing', c: 'w2', from: 3892938, m:[] }, //游金
                {p: 289383,  t: 'shuangYou', c: 'w2', from: 3892938, m:[] }, //双游
                {p: 289383,  t: 'sanYou', c: 'w2', from: 3892938, m:[] }, //三游
                {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
                {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
              ]
     */

    rebuild_controller: function rebuild_controller(data) {
        console.log('执行 rebuild_controller...');
        // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息

        var message = data.message; //收到的数据
        var meUserId = message.p; //客户的userId
        var players = message.players; //四个用户的信息的数组

        //四个用户按加入的顺序由小到大排序
        players.sort(function (a, b) {
            return a.order - b.order;
        });

        var meIndex = msgComm.getMeInfo(players, meUserId); //客户在players数组中的位置

        message.meIndex = meIndex; //将自己的索引存入
        message.room.activeIndex = message.room.activer - 1;

        var isOnline = true;

        for (var i = 0; i < players.length; i++) {
            // //如果拿了一张牌 'w5'，并且拿牌的人不是客户自己，就将牌设置为 '',  表示这个人拿了牌，但是别人不知道是什么牌(因为只有自己才能看到自己拿的牌)
            // if(players[i].napai && meUserId !== players[i].userInfo.userId){
            //     players[i].napai = '';
            // }else{
            //     if(players[i].handCards && players[i].handCards.length === 14){
            //         players[i].napai = players[i].handCards[13];
            //     }
            // }

            if (isOnline && players[i].userInfo.state == 0) {
                isOnline = false;
            }
        }

        var userInfo = players[message.meIndex].userInfo; //客户信息

        //初始始化 客户对象 及 大厅页面
        msgComm.initHall(userInfo);
        getGI('hall').disableHall();

        //初始化房间信息(创建其它三个用户的对象)
        msgComm.initRoom(message);

        this._choose_(message, isOnline);

        if (message.msg) {
            var timer = setInterval(function () {
                if (getGI('rebuildFinished')) {
                    //如果重构完成
                    clearInterval(timer);
                    timer = null;
                    var RebuildController = require('rebuild_msg_controller');
                    var rc = new RebuildController();
                    var msg = window.messageType.convert(message.msg);
                    rc[msg.type + '_controller'] && rc[msg.type + '_controller'](msg);
                }
            }, 50);
        }

        console.log('isOnline: ', isOnline);
    },

    _choose_: function _choose_(message, isOnline) {
        var self = this;

        switch (message.room.step) {
            case '1':
                //创建或加入房间(刚进入房间, 还没发牌)
                //会重新进入大厅页面，然后重新加入房间(发join消息)，所以不算在场景重构中
                room.attr('period', 'readying');

                var timer = setInterval(function () {
                    if (getGI('roomSceneFinished')) {
                        //如果场景已经加载完成
                        clearInterval(timer);
                        var isReady = clientUser.attr('isReady');
                        room.displayRoomInfo({
                            inviteBtn: isReady ? 'showCenter' : 'show', //中间的邀请按钮显示
                            exit: isReady ? 'hide' : 'show' });

                        setGI('rebuildFinished', true);
                        socket.finishMsg('rebuild'); //标记本条消息处理完成
                    }
                }, 50);

                break;
            case '2':
                //开始打牌(已经发牌了--某个人胡牌之前)
                room.attr('period', 'playing');

                var timer = setInterval(function () {
                    if (getGI('roomSceneFinished')) {
                        //如果场景已经加载完成
                        clearInterval(timer);
                        msgStartRun.startRun(message, isOnline); //开始初始化并运行游戏界面
                    }
                }, 50);

                break;
            case '3':
                //单局结束(某个人胡了后--下一局发牌之前)
                room.attr('period', 'endJu');

                var timer = setInterval(function () {
                    if (getGI('roomSceneFinished')) {
                        //如果场景已经加载完成
                        clearInterval(timer);

                        room.displayRoomInfo({
                            inviteBtn: 'hide', //中间的邀请按钮显示
                            exit: 'hide' });

                        setGI('rebuildFinished', true);
                        socket.finishMsg('rebuild'); //标记本条消息处理完成
                    }
                }, 50);

                break;
            case '4':
                //申请解散的过程中
                //显示解散弹出框
                // room.dismissAlert.dismissShow('show');
                var timer = setInterval(function () {
                    if (getGI('roomSceneFinished')) {
                        //如果场景已经加载完成
                        clearInterval(timer);

                        setGI('rebuildFinished', true);
                        socket.finishMsg('rebuild'); //标记本条消息处理完成
                    }
                }, 50);
                break;
        }
    }

});

module.exports = Rebuild_controller;

cc._RFpop();
},{"msgComm":"msgComm","msgStartRun":"msgStartRun","rebuild_msg_controller":"rebuild_msg_controller"}],"rebuild_msg_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '5e8e17fRn9EaIG3g74ZUkNT', 'rebuild_msg_controller');
// scripts\common\controller\messageController\rebuild_msg_controller.js

'use strict';

/**@class
 * @classdesc 处理rebuild中的附加消息
 */
var Rebuild_msg_controller = cc.Class({
    name: 'Rebuild_msg_controller',

    /**
      * 已收到别人出的牌后，自己对这张牌进行处理
      * data: {  e | errMsg: null,
                 m | message: {t:7, m: 'w3', 'p': '10114'} //出牌人的信息
                 s | success: true, t | type: 7, outCard
             }
      */
    outCard_controller: function outCard_controller(data) {
        console.log('执行 重构的 outCard_controller...');
        console.log(data);
        clientUser.send({ type: 'heart', message: data.t }); //回复一条确认消息

        //     //对收到消息进行字段名的转换
        //    var cardMsg =  window.messageType.convert(data.m, 'send_big');  //  {t | type:7 outCard, m | message: 'w3', 'p' | openId: '10114'}

        //     //取得出牌的用户,包括出牌人(主要是要重新计算教)
        //     var user = room.getUser(cardMsg.openId);
        //     user.outCardNoChange(cardMsg.message, cardMsg.openId);

        //     // 如果不是自己发的消息才处理, 因为自己的出牌已经在点击出牌时已经处理过了
        //     if( !room.msgIsMeSend(cardMsg.openId) ){
        //         //检查能对这张牌的操作
        //         clientUser.outCardCheck(cardMsg.message, cardMsg.openId);
        //     }

        //     room.displayPointer(-1); //不指向任何一个人
    },

    /**
     * 胡
     * data: {  e | errMsg: null,
                m | : {t:8, c: 'w2', from: '384827', 'p': '10194'} //胡牌人的信息
                s | success: true, t | type: 8, hu
            }
    */
    hu_controller: function hu_controller(data) {
        console.log('执行 重构的 hu_controller...');
        console.log(data);
        clientUser.send({ type: 'heart', message: data.t }); //回复一条确认消息
        // debugger;
        //var operateMsg =  window.messageType.convert(data.message, 'send_big');  //{ type: 'hu', message: '{huCard: 'w2',from: '384827'}', openId: '10114'}

        // var operateMsg = {};
        // operateMsg.operateName = 'hu';
        // operateMsg.huCard = data.m.c;
        // operateMsg.from = data.m.from;
        // operateMsg.openId = data.m.p;

        // var isFinished = false; //是否已经将胡的消息接收完了

        // //如果是自摸的，就只可能收到一条胡的消息
        // if(operateMsg.openId === operateMsg.from){
        //     isFinished = true;
        //     room['nextZhuang'] = operateMsg.openId ; //第一个胡的人是下一局的庄家
        // //不是自摸的，就一定是点炮的，可能收到多条胡的消息
        // }else{
        //     room['nextZhuang'] = operateMsg.openId ; //第一个胡的人是下一局的庄家
        //     isFinished = true;
        // }
        // var fromWhere;
        // //如果是自摸的
        // if( operateMsg.openId === operateMsg.from ){
        //     fromWhere = 'fromMe';
        // }else{
        //     fromWhere = operateMsg.from;
        // }
        // var user = room.getUser(operateMsg.openId);
        // user.operateItem( {operateName:'hu', operateCard: operateMsg.huCard, from: fromWhere}, true ); //不发胡牌消息
        // var next = room.getNextActiveIndex( operateMsg.openId );  //按顺时针算，下一个用户的位置号

        // console.log('operate: 下一当前用户是： ' + next);

        // room.setActiveUser(next); //设置下一个用户
        // room.displayPointer(next); //指向当前用户并倒计时

        // //如果将所有胡的消息收完了，就开始处理
        // if(isFinished){
        //    var resultData =  { type: 'hu', message: {huCard: operateMsg.huCard ,from: operateMsg.from}, openId: operateMsg.openId}
        //    clientUser.juEnd(resultData);
        //    clientUser.attr('isReady', false);
        // }
    }

});

module.exports = Rebuild_msg_controller;

cc._RFpop();
},{}],"record_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '54f01cXmWZD66neE6YPIRSo', 'record_controller');
// scripts\common\controller\messageController\record_controller.js

'use strict';

/**@class
 * @classdesc 记录查询
 */
var Record_controller = cc.Class({
    name: 'Record_controller',

    /**
     * 记录查询
     * data: {  e | errMsg: null,
                m | message:
                s | success: true, t | type: 50, operate
            }
    */
    record_controller: function record_controller(data) {
        console.log("客户端-- 执行record_controller 战绩查询记录数据:", data);

        /**
        * showRecords函数需要的数据格式
        * dataArr = [
        *      {roomId: '6389292', time: '2016-11-29 14:03',
        *      usersInfo: [ {nickName: '如花',score: -28}, {nickName: '如花',score: 15}, {nickName: '如花',score: -28}, {nickName: '如花',score: -28}]},
        *      {roomId: '6389292', time: '2016-11-29 14:03',
        *      usersInfo: [ {nickName: '如花',score: -11}, {nickName: '如花',score: -95}, {nickName: '如花',score: 4}, {nickName: '如花',score: -28}]},
        * ]
        */
        getGI('hall').recordAlert.showRecords(data.message); //显示记录的数据

        socket.finishMsg('record'); //标记本条消息处理完成
        getGI('hall').loading('end'); //加载完成
    }

});

module.exports = Record_controller;

cc._RFpop();
},{}],"record_serverController":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6165a2q/Z1GWZSvYSqzE7WW', 'record_serverController');
// scripts\server\serverController\record_serverController.js

'use strict';

/**@class
 * @classdesc 记录查询
 */
var Record_serverController = cc.Class({
    name: 'Record_serverController',

    /**
     * 记录查询
     * data: {  openId: 893929,
                message:'',
                type: record
            }
    */
    record_controller: function record_controller(data) {
        console.log("服务器--处理战绩查询记录数据:");

        //showRecords函数需要的数据格式
        var dataArr = [{ roomId: '43300352', time: '2016-11-09 14:03',
            usersInfo: [{ nickName: '昭君', score: -28 }, { nickName: '静怡', score: 15 }, { nickName: '西施', score: -28 }, { nickName: '玉环', score: -28 }]
        }, { roomId: '60000352', time: '2016-11-29 14:03',
            usersInfo: [{ nickName: '小强', score: -11 }, { nickName: '老斯机', score: -95 }, { nickName: '小兵', score: 4 }, { nickName: '如花', score: -28 }]
        }, { roomId: '60000332', time: '2016-11-14 14:03',
            usersInfo: [{ nickName: '周润发', score: -11 }, { nickName: '张娜拉', score: -95 }, { nickName: '赵丽颖', score: 0 }, { nickName: '刘德华', score: 8 }]
        }, { roomId: '73838293', time: '2016-12-29 14:03',
            usersInfo: [{ nickName: '张三丰', score: -11 }, { nickName: '张无忌', score: -95 }, { nickName: '周芷若', score: 4 }, { nickName: '灭绝师太', score: -28 }]
        }];

        localServer.send({ type: 'record', message: dataArr });
    }

});

module.exports = Record_serverController;

cc._RFpop();
},{}],"roomData":[function(require,module,exports){
"use strict";
cc._RFpush(module, '73476WUBiNB36yuEjmDpauJ', 'roomData');
// scripts\room\model\room\roomData.js

'use strict';

/**
 * @class
 * @classdesc -- 房间信息
 */
var RoomData = cc.Class({
        name: 'RoomData',

        /**
         * @constructor
         */
        ctor: function ctor() {

                this.isSetTing = false; //是否允许有听的功能, false表示不允许，true表示允许

                /* false表示 中发白三个不一样的不能组成一个顺子，只能用来碰，刻子，各种扛。
                 * true表示 中发白三个不一样的能组成一个顺子，只能用来碰，刻子，各种扛
                 * 用于判断能不能胡牌和能不能吃中发白
                 */
                this.isZFBOrder = false; //中发白不能组成顺子，只能用作碰、杠

                this.ju = ''; //第一个选项 8局--1 16局--2， 分数打法：100分--3 200分--4
                this.diFeng = '', //第二个选项 2分--1 4分--2 8分--3
                this.fanXin = '', //第三个选项 单游不能平到--1 双游不能平到--2

                //记录下一局的庄家
                this.nextZhuang = '';

                //每一局开始时，记录已经有多少个人准备好了
                this.readyArr = [];
                this.period = 'playing'; //目前的阶段 'readying' 'endJu' 'allShow'
                this.playingPeriod = 'beiMian'; //beiMian -- 背面的牌显示 mix - 乱序显示牌  order -- 有序显示牌 buHua -- 一个用户补花完成

                this.showChange = false; //标记出的牌是否已经变小了

                this.dismissingNum = 0; //记录收到的解散房间消息的数量
                this.isCanShowAllResult = false; //是否可以显示总结算页面(因为收到juScore时，就会收到roomScore)
                this.isCanDismiss = true; //true才可以点击解散按钮，只有四个人同时在线才能解散，在解散过程中，一但有人离线，就解散失败

                this.restCardNum = 83; //剩余牌的总数, 注意一定要是83张，如果设置为0则庄家起手的补扛就检测不出来

                this.buHuaCallback = []; //补花
                this.buHuaOnceT = 0.8; //发牌时，每一次补花的时间间隔 0.8s
        }

});

module.exports = RoomData;

cc._RFpop();
},{}],"roomDisplay":[function(require,module,exports){
"use strict";
cc._RFpush(module, '39d8f6WBHNMqbO9kQabFR/D', 'roomDisplay');
// scripts\room\view\roomLayer\roomDisplay.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * @class
 * @classdesc 房间的显示
 */
var RoomDisplay = cc.Class({
    name: 'RoomDisplay',

    /**
     * @constructor
     */
    ctor: function ctor() {
        this.restCardNum = cc.find('Canvas/roomMain/topLeft/rest');
        this.mj_Atlas = getGI('roomGlobalRes').mj_Atlas; //麻将牌的图集
        this.num = 0; //
    },

    /**
    * 显示、隐藏、设置头像周围的数据
    * 示例： displayRoomInfo('roomId', 80000); //显示并更新房间号
    *        displayHead({roomId: 80000, juCount: 7}); //更改房间号、当前局数
    * @param {string|Object} 当传入的是string时，需要配合第二个参数
    *                       attrName_obj可取：'roomId'，'juCount' 'inviteBtn' 第二个参数可以是 'show' 或 'hide'
    *                       如果是juCount ，则第二个参数应该是一个对象 {currentJuNum: 3, totalJuNum: 8 }
    * @param {string} value -- 当第一个参数是对象时，可省略
    */
    displayRoomInfo: function displayRoomInfo(attrName_obj, value) {
        if ((typeof attrName_obj === 'undefined' ? 'undefined' : _typeof(attrName_obj)) === 'object') {
            for (var prop in attrName_obj) {
                this._setOneAttrDisplay_(prop, attrName_obj[prop]);
            }
        } else {
            this._setOneAttrDisplay_(attrName_obj, value);
        }
    },

    /**
     * 显示、隐藏、设置一项数据
     * @private
     * @param {string} attrName -- attrName
     * @param {string} value -- 不可省略
     */
    _setOneAttrDisplay_: function _setOneAttrDisplay_(attrName, value) {
        switch (attrName) {
            case 'roomId':
                //设置显示房间号
                cc.find('Canvas/roomMain/topLeft/roomId').getComponent(cc.Label).string = '房间: ' + value;
                break;
            case 'juCount':
                //当前局数的显示

                value = value.totalJuNum === 1 ? '局数：' + value.currentJuNum + '/' + '8局' : value.totalJuNum === 2 ? '局数：' + value.currentJuNum + '/' + '16局' : value.totalJuNum === 3 ? '分数：' + value.currentJuNum + '/' + '100分' : value.totalJuNum === 4 ? '分数：' + value.currentJuNum + '/' + '200分' : 0;
                cc.find('Canvas/roomMain/topLeft/currentProgress').getComponent(cc.Label).string = value;
                break;
            case 'inviteBtn':
                //中间的邀请微信好友按钮
                var inviteBtnNode = cc.find('Canvas/roomMain/invateIcon');
                if (value === 'show' || value === 'hide') {
                    inviteBtnNode.active = value === 'show' ? true : false;
                } else if (value === 'moveCenter') {
                    inviteBtnNode.x = 0;
                } else if (value === 'showCenter') {
                    inviteBtnNode.active = true;
                    inviteBtnNode.x = 0;
                }
                break;
            case 'goWixin':
                //返回微信按钮
                cc.find('Canvas/roomMain/topRight/goWiXinIcon').active = value === 'show' ? true : false;
                break;
            case 'goldCard':
                //显示金牌
                var goldNode = cc.find('Canvas/playingLayer/user0/gold');
                if (value === 'show') {
                    goldNode.active = true;
                } else if (value === 'hide') {
                    goldNode.active = false;
                } else {
                    goldNode.active = true;
                    goldNode.getChildByName('hua').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(value);
                }
                break;
            case 'dismiss':
                //解散房间按钮
                cc.find('Canvas/roomMain/topRight/dismissIcon').active = value === 'show' ? true : false;
                break;
            case 'playingLayer':
                //控制麻将层显示
                cc.find('Canvas/playingLayer').active = value === 'show' ? true : false;
                break;
            case 'shareFriend':
                //分享提示层显示
                cc.find('Canvas/shareFriend').active = value === 'show' ? true : false;
                break;
            case 'restCardNum':
                //刷新剩余的牌的数量
                if (value === 'show' || value === 'hide') {
                    this.restCardNum.active = value === 'show' ? true : false;
                } else {
                    this.restCardNum.getChildByName('restNum').getComponent(cc.Label).string = value;
                }

                break;
            case 'arithmetic':
                //选择的算法的显示
                var node = cc.find('Canvas/roomMain/playMethod');

                value = room.attr({ 'diFeng': undefined, 'fanXin': undefined }); //底分和番型

                if (value === 'show' || value === 'hide') {
                    node.active = value === 'show' ? true : false;
                } else {
                    node.getComponent(cc.Label).string = '底:' + value.diFeng + ' 番:' + value.fanXin;
                }

                break;
            case 'timer':
                //中间的倒计时的显示
                cc.find('Canvas/roomMain/timer').active = value === 'show' ? true : false;
                break;
            case 'exit':
                //右上角的退出房间按钮显示
                cc.find('Canvas/roomMain/topRight/leaveRoomIcon').active = value === 'show' ? true : false;
                break;
            case 'exitBtn':
                //右上角的退出房间按钮显示
                cc.find('Canvas/roomMain/topRight/exitIcon').active = value === 'show' ? true : false;
                break;
            case 'setting':
                //右上角的退出房间按钮显示
                cc.find('Canvas/roomMain/topRight/settingIcon').active = value === 'show' ? true : false;
                break;
            case 'dice':
                //中间的倒计时中的骰子的显示
                cc.find('Canvas/roomMain/timer/border').active = value === 'show' ? true : false;
                break;
            case 'offLine': //离线提示
            case 'onLine':
                //上线 或 准备好了的动画提示
                if (value) {
                    cc.find('Canvas/reminder/word').getComponent(cc.Label).string = value;
                }
                cc.find('Canvas').getComponent(cc.Animation).play(attrName);
                break;
        }
    }

});

module.exports = RoomDisplay;

cc._RFpop();
},{}],"roomGlobalRes":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f3e56Pk1cZD25tAIZyJpFtH', 'roomGlobalRes');
// scripts\room\view\roomLayer\roomGlobalRes.js

'use strict';

/**
 * @class
 * @classdesc 获取页面相关数，以后创建对象时用, 挂载到Canvas节点上
 */
cc.Class({
    extends: cc.Component,

    properties: {
        mj_Atlas: cc.SpriteAtlas, //牌的图集
        operate_Atlas: cc.SpriteAtlas, //操作的图集
        dimiss_Atlas: cc.SpriteAtlas, //解散房间的图集
        roomMain_Atlas: cc.SpriteAtlas, //房间页面的图集

        face_Atlas: cc.SpriteAtlas, //表情的图集
        single_Atlas: cc.SpriteAtlas, //单局结算页面的图集
        singleUserPref: cc.Prefab, //单局结算页面中的用户节点
        allResultUserPref: cc.Prefab, //八局结算页面中的用户节点

        playingLayer: cc.Node, //麻将层节点
        facePref: cc.Prefab, //单个表情节点
        wordPref: cc.Prefab },

    onLoad: function onLoad() {
        setGI('roomGlobalRes', this); //存入全局
        console.log('存入全局');
    }
});

cc._RFpop();
},{}],"room":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a17d9JJwypGtL/TTtERyKT5', 'room');
// scripts\room\model\room\room.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var Combination = require('combination'); //状态类

var RoomDisplay = require('roomDisplay'); //房间信息的显示

var DismissAlert_display = require('dismissAlert_display'); // 解散房间弹出框
var MessageAlert_display = require('messageAlert_display'); // 信息弹出框
var SingleResult_display = require('singleResult_display'); // 单局结算弹出框
var ResultAlert_display = require('allResultAlert_display'); // 八局结算弹出框

;
/**
 * @class
 * @classdesc -- 房间类
 */
var Room = cc.Class({
    name: 'Room',
    extends: Combination,

    properties: {
        roomId: 0, //房间号
        totalJuNum: 0, //总局数
        currentJuNum: 0, //当前局数--每一局更新
        totalQuan: 0, //总圈数
        currentQuan: 0, //当前圈数
        usersArr: [], //存放加入的所有用户，是以用户坐位顺序来存的，usersArr[0]永远是客户自己
        acitveIndex: 0, //当前活动用户的位置号
        userIdOrder: null, //{userId1: user1, userId2: user2}
        zhuang: 0, //庄家索引
        goldCard: '' },

    /**
     * @constructor
     */
    ctor: function ctor() {
        var config = arguments[0];

        if (!config) {
            return;
        }
        for (var prop in config) {
            //config不会创建新的属性
            if (this[prop] !== undefined) {
                this[prop] = config[prop];
            }
        }
        this.userIdOrder = {};
        this.currentUser = this.usersArr[0]; //当前活动用户,默认为庄家

        this.combination([{ name: 'roomData', combineType: 'data', type: 'class' }]);
    },

    /**
     * 组合其它类的方法
     * @public
     */
    combineAction: function combineAction() {
        this.combination([{ name: 'animate_head', combineType: 'method', type: 'class' }, //四个用户的头像运动到一侧
        { name: 'animate_skip', combineType: 'method', type: 'class' }, //每次出牌后的跳动动画
        { name: 'animate_countDown', combineType: 'method', type: 'class' }, //中间的指示器
        { name: 'roomDisplay', combineType: 'method', type: 'class' }]);
        var roomGlobalRes = getGI('roomGlobalRes');
        // 解散房间弹出框
        this.dismissAlert = new DismissAlert_display();

        // 信息弹出框
        this.messageAlert = new MessageAlert_display();

        // 单局结算弹出框
        this.singleResult = new SingleResult_display();

        // 八局结算弹出框
        this.resultAlert = new ResultAlert_display();
    },

    reset: function reset() {
        this.reset_other();
    },

    resetMah: function resetMah() {
        this.reset();
        for (var i = 0; i < 4; i++) {
            //遍历所有人
            this.usersArr[i].reset();
        }
    },

    /**
     * 加入一个用户，默认活动用户是庄家
     * @public
     * @param {User} user -- 用户对象
     */
    addUser: function addUser(user) {
        this.usersArr.push(user);
        this.userIdOrder[user.attr('userId')] = user;

        if (user.attr('zhuang')) {
            //如果是庄家
            this.acitveIndex = user.attr('positionOrder'); //就记录活动位置
            this.zhuang = this.acitveIndex;
        }

        var len = this.usersArr.length;
        if (len === 4) {
            //加满了后，按用户的位置号排序
            this.usersArr.sort(function (a, b) {
                return a.attr('positionOrder') - b.attr('positionOrder');
            });

            setGI('joinOver', true);
        }
        console.log(this.userArr);
    },

    /**
    * 删除一个用户(加错了房间后，点击退出时)
    * @public
    * @param {User} user -- 用户对象
    */
    removeUser: function removeUser(user) {
        var index;
        if (this.usersArr === 4) {
            //如果加满了（已经排过序了）
            index = user.attr('positionOrder');
        } else {
            var joinOrder = user.attr('joinOrder');
            for (var i = 0; i < this.usersArr.length; i++) {
                if (this.usersArr[i].attr('joinOrder') === joinOrder) {
                    index = i;
                    break;
                }
            }
        }

        if (typeof index === 'number') {
            this.usersArr.splice(index, 1); //删除用户
            setGI('joinOver', false);
        }
    },

    /**
     * 遍历其它人
     * @public
     */
    otherUserForEach: function otherUserForEach(callback) {
        for (var i = 1; i < 4; i++) {
            //遍历其它人
            callback.call(this, this.usersArr[i], i);
        }
    },

    /**
     * 遍历所有人
     * @public
     */
    forEach: function forEach(callback) {
        var result;
        outer: for (var i = 0; i < 4; i++) {
            //遍历所有人
            result = callback.call(this, this.usersArr[i], i);
            switch (result) {
                case 'break':
                    break outer;
                case 'continue':
                    continue outer;
            }
        }
    },

    /**
     * 设置活动用户,通过 用户的 位置号index 或 用户对象 或 用户openId
     * @public
     * @param {number|object|string} -- number: 用户的位置号  object: 用户对象  string: 用户的openId
     * @returns {number} -- 活动用户的位置号
     */
    setActiveUser: function setActiveUser(key) {
        this.usersArr[this.acitveIndex].attr('isActive', false); //先前的活动用户不再活动

        switch (typeof key === 'undefined' ? 'undefined' : _typeof(key)) {//传入的是位置号或userId
            case 'number':
                if (key < 10) {
                    this.acitveIndex = key;
                } else {
                    this.acitveIndex = this.userIdOrder[key].attr('positionOrder');
                }

                break;
            case 'object':
                //传入的是用户对象
                this.acitveIndex = key.attr('positionOrder');
                break;
            case 'string':
                //传入的是用户openId
                // this.acitveIndex = this.userIdOrder[key].attr('positionOrder');
                break;
        }

        this.usersArr[this.acitveIndex].attr('isActive', true); //更新活动用户
        return this.acitveIndex;
    },

    /**
     * 获取位置号,通过 用户的 用户对象 或 用户openId 或 用户id
     * @public
     * @param {number|object|string} -- number: 用户id  object: 用户对象  string: 用户的openId
     */
    getPositionOrder: function getPositionOrder(key) {
        switch (typeof key === 'undefined' ? 'undefined' : _typeof(key)) {//传入的是位置号
            case 'string': //传入的是用户openId
            // return this.userIdOrder[key].attr('positionOrder');
            case 'object':
                //传入的是用户对象
                return key.attr('positionOrder');
            case 'number':
                if (key < 10) {
                    return key;
                } else {
                    return this.userIdOrder[key].attr('positionOrder');
                }
        }
    },

    /**
     * 返回用户信息 通过 用户的 位置号index 或 用户openId 或 用户userId 或 用户对象本身
     * @public
     * @param {number|object|string} -- number: 用户的位置号  object: 用户对象  string: 用户的openId
     * @returns {User} -- 查询的用户
     */
    getUser: function getUser(key) {
        switch (typeof key === 'undefined' ? 'undefined' : _typeof(key)) {
            case 'number':
                //传入的是位置号
                if (key < 10) {
                    if (this.usersArr.length === 4) {
                        //说明已经排好序了
                        return this.usersArr[key];
                    } else {
                        //如果人都还没加齐
                        var arr = this.usersArr;
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i].attr('positionOrder') === key) {
                                return arr[i];
                            }
                        }
                    }
                } else {
                    return this.userIdOrder[key];
                }

                break;
            case 'object':
                //传入的是用户对象
                return key;
            case 'string': //传入的是用户openId
            // return this.userIdOrder[key];
        }
    },

    /**
     * 返回下一个用户的位置号 , 拿牌时，按顺时针确定拿牌的用户
     * @public
     * @param {number|object|string} -- number: 用户的位置号  object: 用户对象  string: 用户的openId
     * @returns {number} -- 返回位置号
     */
    getNextActiveIndex: function getNextActiveIndex(key) {
        switch (typeof key === 'undefined' ? 'undefined' : _typeof(key)) {//传入的是位置号
            case 'string': //传入的是用户openId
            // return this.userIdOrder[key].attr('positionOrder');
            case 'object':
                //传入的是用户对象
                return key.attr('positionOrder');
            case 'number':
                if (key < 10) {
                    return this.acitveIndex - 1 < 0 ? 3 : this.acitveIndex - 1;
                } else {
                    return this.userIdOrder[key].attr('positionOrder');
                }
            default:
                return this.acitveIndex - 1 < 0 ? 3 : this.acitveIndex - 1;
        }
    },

    /**
     * 消息是不是我自己发的
     * @public
     * @param {string} openId -- 发消息人的userId
     * @returns {boolean} -- true表示是自己发的消息， false表示是别人发的消息
     */
    msgIsMeSend: function msgIsMeSend(userId) {
        return clientUser.attr('userId') === userId;
    },

    /**
     * 设置某个人为庄家
     */
    setZhuang: function setZhuang(key) {
        var user = this.getUser(key);
        this.acitveIndex = user.attr('positionOrder');
        this.usersArr[this.zhuang].attr('zhuang', false);
        this.zhuang = this.acitveIndex;
        user.attr('zhuang', true);
    },

    /**
     * 将加入的顺序号转换为用户的位置号
     */
    convertJtoP: function convertJtoP(joinOrder) {
        for (var i = 0; i < this.usersArr.length; i++) {
            if (this.usersArr[i].attr('joinOrder') === joinOrder) {
                return this.usersArr[i].attr('positionOrder');
            }
        }
    }

});

module.exports = Room;

cc._RFpop();
},{"allResultAlert_display":"allResultAlert_display","combination":"combination","dismissAlert_display":"dismissAlert_display","messageAlert_display":"messageAlert_display","roomDisplay":"roomDisplay","singleResult_display":"singleResult_display"}],"serverControllerMapMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '859c5kFRxlGbJugqW2Pgrpc', 'serverControllerMapMng');
// scripts\server\serverControllerMapMng.js

'use strict';

/**@class
 * @classdesc 用于调用各种消息
 */
var ServerControllerMapMng = cc.Class({
    name: 'ServerControllerMapMng',

    /**
     * @constructor
     * @param {Object} config -- {
     *      controllerMap: //必须, 控制器地图
     * }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.controllerMap = config.controllerMap;

        this.controllerSave = {}; //用来存储所有的控制器对象 格式为：消息名1: 对象
        this.controllerObjArr = []; //所有 的控制器对象
        this._init_(); //初始化
    },

    /**
     * 调用所有控制器对象上的reset方法进行重置
     * @public
     */
    resetController: function resetController() {
        var arr = this.controllerObjArr;
        for (var i = 0; i < arr.length; i++) {
            arr[i].reset && arr[i].reset();
        }
    },

    /**
     * 访问消息的控制器
     * @public
     * @param {Object} data -- 接收到的数据  如：{errMsg: string, type: string, message: some, success: boolean}
     */
    accessController: function accessController(data) {

        var msgType = data.type,
            //消息的类型名 如: 'join'  'anGang'
        controllerObj = this.controllerSave[msgType]; //控制器对象
        if (!controllerObj) {
            //如果没有专门处理
            console.log('服务器没有专门处理', data);
            return;
        }
        var indexController = controllerObj['index'],
            //控制器的主方法
        msgController = controllerObj[msgType + '_controller']; //消息的控制方法

        indexController && indexController.call(controllerObj, data); //调用主方法
        msgController && msgController.call(controllerObj, data); //调用控制方法
    },

    /**
     * 创建控制器对象
     * @private
     */
    _init_: function _init_() {
        this.className; //类名
        var controllerMap = this.controllerMap;
        for (var prop in controllerMap) {
            this.className = require(prop); //引入文件
            var controllerObj = new this.className(); //控制器对象
            controllerObj.fileName = prop; //控制器对象上 存储文件名

            this.controllerObjArr.push(controllerObj);

            var msgNameArr = this.controllerMap[prop]; //消息名的数组
            for (var i = 0; i < msgNameArr.length; i++) {
                this.controllerSave[msgNameArr[i]] = controllerObj;
            }
        }
    }
});

module.exports = ServerControllerMapMng;

cc._RFpop();
},{}],"serverGlobal":[function(require,module,exports){
"use strict";
cc._RFpush(module, '086868yRNVGjZY9OD0h2i7T', 'serverGlobal');
// scripts\server\serverGlobal.js

'use strict';

window.serverData = {}; //服务器数据

var serverMsgConfig = require('serverMsgConfig');
window.serverControllerMap = serverMsgConfig.controllerMap; //服务器消息的地图
window.serverMessageType = serverMsgConfig.messageType; //消息格式转换
var MsgsMng = require('msgsMng');
window.localServer = new MsgsMng();
/**
 * 服务器模式
 * rebuild -- 登录后直接进行重构  autoJoin -- 创建房间后自动加入其它三个人
 * autoOutCard -- 其它三个人自动出牌
 */

window.serverModel = 'rebuild';

cc._RFpop();
},{"msgsMng":"msgsMng","serverMsgConfig":"serverMsgConfig"}],"serverMsgConfig":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ce3cb+d20NJDagRMwK+M08j', 'serverMsgConfig');
// scripts\server\serverMsgConfig.js

'use strict';

var clientMessageType = require('msgConfig').messageType;

//控制器地图
var controllerMap = {
    // napai_serverController: ['napai'],  // 文件名：[消息名1, 消息名2, 消息名3]
    // juEnd_serverController: ['juScore', 'roomScore'],
    // msgSend_serverController: ['word', 'face'],
    // // operateCheck_controller: ['operateCheck'],
    // // operateClick_controller: ['operateClick'],
    operate_serverController: ['buGang', 'anGang', 'ting', 'hu', 'mingGang', 'chi', 'pong', 'specialGang', 'specialBuGang', 'guo'],
    // outCard_serverController: ['outCard'],
    start_serverController: ['login', 'start'],
    // dismiss_serverController: ['ready', 'dismissing', 'dismiss'],
    join_serverController: ['create', 'join'],
    // buGangCheck_serverController: ['wantBuGang', 'buGangCheck'],
    record_serverController: ['record'],
    isOnline_serverController: ['leaveLine', 'reConnect', 'exitRoom']
};

var messageType = {};
messageType.convert = function (dataObj) {
    var resultData = {};;
    if ('success' in dataObj) {
        //说明dataObj是收到的数据

        //{e: null, s: true, m: {aa:34}, t:5} --> {errMsg: null, success: true, message: {aa:34}, type: 'login'}
        resultData.e = dataObj.errMsg; //错误信息 {string}
        resultData.t = clientMessageType[dataObj.type]; //类型名字 {string}
        resultData.m = dataObj.message; //消息内容 {object}
        resultData.s = dataObj.success; //是否成功, {boolean}
    } else {
        //说明dataObj是要发送的数据

        //{openId: null,  message: {aa:34}, type: 'login'} --> { m: {ab: 39}, p: 38992833, t:5}
        resultData.type = clientMessageType[dataObj.t]; //消息类型 {string}
        resultData.message = dataObj.m; //消息内容 {object}
        resultData.openId = dataObj.p; //发送人的openId, {string}
    }

    return resultData;
};

module.exports = { messageType: messageType, controllerMap: controllerMap };

cc._RFpop();
},{"msgConfig":"msgConfig"}],"serverMsgData":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a28906wxNJE/4u/8tcM7U1C', 'serverMsgData');
// scripts\server\serverMsgData.js

'use strict';

var msgData = {
    /**
     * 发送开始消息 到 客户端
     */
    sendStartMsg: function sendStartMsg() {
        var arr = [];
        for (var i = 0; i < 4; i++) {
            var temp = [];
            var handCards = window.testData.test_cardsData[i].handCards;
            if (i === 0) {
                for (var j = 0; j < handCards.length; j++) {
                    temp.push(handCards[j]);
                }
            } else {
                for (var j = 0; j < handCards.length; j++) {
                    temp.push('');
                }
            }
            arr.push(temp);
        }
        var clientUserId = window.testData.test_users[0].userId;
        var msg = [{ p: clientUserId,
            hua: window.testData.test_cardsData[0].hua, //[ {h: 'i2', c: 'i3'}, {h: 'i3', c: 'w2'} ], //没有补花时，为 null
            c: arr[0], //[ 'i2', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2', 't8', 'w2', 't8', 'w2', 'n'] //玩家拿到的牌
            gc: 't4' }, { p: window.testData.test_users[1].userId,
            hua: window.testData.test_cardsData[1].hua, //[ {h: 'i2', c: ''}, {h: 'i3', c: ''} ], //没有补花时，为 null
            c: arr[1], //[ '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''] //玩家拿到的牌
            gc: 't4' }, { p: window.testData.test_users[2].userId,
            hua: window.testData.test_cardsData[2].hua, //[ {h: 'i2', c: ''}, {h: 'i3', c: ''} ], //没有补花时，为 null
            c: arr[2], //[ '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''] //玩家拿到的牌
            gc: 't4' }, { p: window.testData.test_users[3].userId,
            hua: window.testData.test_cardsData[3].hua, //[ {h: 'i2', c: ''}, {h: 'i3', c: ''} ], //没有补花时，为 null
            c: arr[3], //[ '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''] //玩家拿到的牌
            gc: 't4' }];
        //第一个人起手能进行的操作
        var optArr = [{ p: clientUserId, t: 'pong', c: 't9', from: clientUserId, m: ['t9', 't9'] }, //碰
        { p: clientUserId, t: 'mingGang', c: 'w3', from: clientUserId, m: ['w3', 'w3', 'w3'] }, //明杠
        { p: clientUserId, t: 'anGang', c: 'w1', from: clientUserId, m: ['w1', 'w1', 'w1', 'w1'] }, //暗杠
        { p: clientUserId, t: 'anGang', c: 'w2', from: clientUserId, m: ['w2', 'w2', 'w2', 'w2'] }, //暗杠
        { p: clientUserId, t: 'anGang', c: 'w2', from: clientUserId, m: ['w2', 'w2', 'w2', 'w2'] }, //暗杠
        // {p: '289383',  t: 'buGang', c: 'w3', from: '3892938', m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
        { p: clientUserId, t: 'chi', c: 't3', from: clientUserId, m: ['t2', 't4'] }, //吃
        { p: clientUserId, t: 'chi', c: 't3', from: clientUserId, m: ['t4', 't5'] }];

        //发送开始消息 到 客户端
        localServer.send({ type: 'start', message: msg, errMsg: TOOL.converOperate(optArr) });

        this.autoOutCard(); //(除了自己)自动拿牌和出牌
    },

    /**
     * (除了自己)自动拿牌和出牌
     */
    autoOutCard: function autoOutCard() {

        window.serverData.currenIndex = 0;

        var clickNum = 0;
        var napai = '';
        function doubleClick() {
            clickNum++;
            var userId = window.testData.test_users[window.serverData.currenIndex].userId;

            if (clickNum === 1) {
                var userCardsData = window.testData.test_cardsData[window.serverData.currenIndex];
                if (window.serverData.currenIndex === 0 && userCardsData.naiPaiIndex === undefined) {
                    userCardsData.naiPaiIndex = 0;
                    return;
                }
                if (userCardsData.naiPaiIndex === undefined) {
                    userCardsData.naiPaiIndex = 0;
                }
                napai = userCardsData.naPaiCards[userCardsData.naiPaiIndex];
                userCardsData.naiPaiIndex++;
                localServer.send({ type: 'napai', message: { card: napai, cardNum: 43, who: userId } });
            } else if (clickNum === 2) {
                window.serverData.currenIndex--;
                if (window.serverData.currenIndex < 0) {
                    window.serverData.currenIndex = 3;
                }
                if (window.serverData.currenIndex !== 3) {
                    localServer.send({ type: 'outCard', message: { t: 7, m: napai, p: userId } });
                }
                clickNum = 0;
            }
        }

        if ('touches' in cc.sys.capabilities) {
            cc.find('Canvas/roomMain/timer').on('touchend', doubleClick, this);
        } else if ('mouse' in cc.sys.capabilities) {
            cc.find('Canvas/roomMain/timer').on('mouseup', doubleClick, this);
        }
    }
};
module.exports = msgData;

cc._RFpop();
},{}],"share_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, '9f0f1adUWZOO7R7SFuMn/Fn', 'share_controller');
// scripts\common\controller\messageController\share_controller.js

'use strict';

/**@class
 * @classdesc 分享
 */
var Share_controller = cc.Class({
    name: 'Share_controller',

    /**
    * 处理分享消息
    * data: {  e | errMsg: null,
               m | message: "{appid:'wxa0c15c83d5c0f91f', noncestr:'9jxoxkjs8rty4th',
                   signature: '5ea009cca12a897a2e6d011b12cf71c1d452018f', timestamp: '1491552100',
                   url: 'http://www.uitrs.com'
               s | success: true, t | type: 37, share
           }
    */
    share_controller: function share_controller(data) {
        console.log('客户端-- 执行share_controller  分享消息：', data);

        if (getGI('model') !== 'produce') {
            socket.finishMsg('share'); //标记本条消息处理完成
            return;
        }

        console.log('微信对象：', wx);
        var message = JSON.parse(data.message);
        console.log('收到的分享数据：', message);

        var shareConfig = {
            title: '泉洲麻将', // 分享标题
            link: message.url + '/qzmj', // 分享链接
            desc: "咱泉洲人自己的麻将",
            // 分享图标
            imgUrl: message.url + '/img/shareIcon.png',
            trigger: function trigger(res) {
                console.log('请点击浏览器右上角的分享到朋友圈');
            },
            success: function success(res) {
                console.log('分享成功');
            },
            cancel: function cancel(res) {
                console.log('已取消分享');
            },
            fail: function fail(res) {
                console.log("分享失败");
            }
        };

        setGI('shareConfig', shareConfig); //存入全局

        var obj = {
            // 开启调试模式,调用的所有api的返回值会在客户端alert出来，
            //若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            debug: false,
            appId: message.appid, // 必填，公众号的唯一标识
            timestamp: message.timestamp, // 必填，生成签名的时间戳
            nonceStr: message.noncestr, // 必填，生成签名的随机串
            signature: message.signature, // 必填，签名，见附录1

            // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
        };

        wx.config(obj);
        console.log('获得了分享的数据：', obj);

        wx.ready(function () {
            wx.onMenuShareTimeline(shareConfig); //分享给朋友
            wx.onMenuShareAppMessage(shareConfig); //分享到朋友圈
            console.log('添加了分享的数据：', shareConfig);
        });

        socket.finishMsg('share'); //标记本条消息处理完成
    },

    /**
     * 处理心跳消息
     * data: {  e | errMsg: null,
                m | message: null,
                s | success: true, t | type: 6, heart
            }
     */
    heart_controller: function heart_controller(data) {
        // console.log('客户端-- 执行 heart_controller  心跳消息');
        clientUser.send({ type: 'heart', message: data.message });
        socket.finishMsg('heart'); //标记本条消息处理完成
    }

});

module.exports = Share_controller;

cc._RFpop();
},{}],"singleResult_click":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8d825HCdj1Pxom5//OgHVqJ', 'singleResult_click');
// scripts\room\view\alert\singleResult_click.js

'use strict';

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

/**
 * 单局结算页面上的事件, 挂载在 Canvas/singleResult节点上
*/
cc.Class({
        extends: cc.Component,

        // /**
        //  * 准备按钮
        //  * 1.挂载于 页面中间的准备按钮上
        //  */
        // readyClick: function(event){
        //     // audio.play('waterClick');
        //     event.target.active = false; //隐藏本按钮
        //     clientUser.send( {message: '准备好了', type: 'ready'} ); //发送消息
        //     clientUser.attr('isReady', true);
        //     clientUser.displayHead('ok', 'show'); //显示ok状态
        //     //邀请好友按钮移动到中间去
        //     room.displayRoomInfo({'exit': 'hide'});
        // },


        /**
         * 继续按钮，注意，跟准备按钮不一样
         * 1.挂载于 单局结算页面中的继续按钮上
         * @public
         */
        continueClick: function continueClick(event) {
                var _room$displayRoomInfo;

                window.audio.playEffect('waterClick'); //播放点击声音

                room.attr('isCanShowAllResult', true); //可以显示总结算界面

                //隐藏结算页面
                room.singleResult.singelResultShow('hide');

                //重置环境
                room.resetMah();

                //中间的倒计时框隐藏
                room.timeShow('hide');

                //更改庄家
                room.setZhuang(room['nextZhuang']);

                //中间的准备按钮隐藏
                room.displayRoomInfo((_room$displayRoomInfo = { 'inviteBtn': 'hide', 'exit': 'hide', 'goldCard': 'hide',
                        'dismiss': 'show', 'goWixin': 'show', 'restCardNum': 'hide' }, _defineProperty(_room$displayRoomInfo, 'restCardNum', 'hide'), _defineProperty(_room$displayRoomInfo, 'exitBtn', 'hide'), _defineProperty(_room$displayRoomInfo, 'setting', 'hide'), _room$displayRoomInfo));

                clientUser.send({ message: '准备好了', type: 'ready' }); //发送消息
                clientUser.attr('isReady', true);
                clientUser.displayHead('ok', 'show'); //显示ok状态

                //显示每个人的总分数 和 庄家
                room.forEach(function (user) {
                        user.displayHead('score', user.attr('roomScore')); //显示分数
                        user.displayHead('zhuang', user.attr('zhuang') ? 'show' : 'hide'); //显示 庄家
                });

                var readyArr = room.attr('readyArr');

                var user = void 0;
                //显示ok状态(已经准备好了的人)，不包括自己
                for (var i = 0; i < readyArr.length; i++) {
                        user = room.getUser(readyArr[i]);
                        user.displayHead('ok', 'show');
                }
        },

        /**
         * 解散按钮
         * 1.挂载于 单局结算页面中的解散按钮上
         * @public
         */
        dismissClick: function dismissClick(event, isReset) {
                window.audio.playEffect('waterClick'); //播放点击声音

                if (!room.attr('isCanDismiss')) {
                        room.displayRoomInfo('onLine', '只有房间中的全部成员同时在线时，才能解散房间！');
                        return;
                }

                room.attr('isCanShowAllResult', false); //不可以显示总结算界面

                //隐藏结算页面
                // room.singleResult.singelResultShow('hide');

                room.dismissAlert.startDismiss(); //显示解散弹出框

                //重置环境
                // room.resetMah();


                //更改庄家
                // room.setZhuang( room['nextZhuang'] );

                //中间的倒计时框隐藏
                // room.timeShow('hide');

                //显示每个人的总分数 和 ok
                // room.forEach(function(user){
                //     user.displayHead('score', user.attr('roomScore')); //显示分数
                // });

                // var readyArr = room.attr('readyArr');

                // //显示ok状态(已经准备好了的人)，不包括自己
                // for(var i=0; i<readyArr.length ; i++){
                //     room.getUser(readyArr[i]).displayHead('ok', 'show');
                // }
        }

});

cc._RFpop();
},{}],"singleResult_display":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c7972sQZytBw48WmNEieZgy', 'singleResult_display');
// scripts\room\view\alert\singleResult_display.js

'use strict';

/**
 * @class
 * @classdesc -- 单局结束弹出框
 */
var SingleResult = cc.Class({
    name: 'SingleResult',

    ctor: function ctor() {
        this.singleResult = cc.find('Canvas/singleAlert'); //弹出框节点
        this.mj_Atlas = getGI('roomGlobalRes').mj_Atlas; //牌的图集
        this.singleUserPref = getGI('roomGlobalRes').singleUserPref; //预制节点
        this.single_Atlas = getGI('roomGlobalRes').single_Atlas; //单局结算页面的图集

        this.mjPosition = []; //存放麻将的初始坐标，用于左右移动
        this.mjArrPosition = ''; //存放麻将的数组的初始坐标，用于上下移动
        this.itemNodes = []; //4个用户的父节点数组
        this._getItemNodes_(); //获取每个用户的节点 （将数据获取后，放入 this.itemNodes中）

        this.scores = [0, 0, 0, 0]; //记录四个人的总分数
        this.isShow = false; //记录目前的显示状态

        this.juResults = null; //记录收到的四个用户的数据
    },


    /**
    * 本弹出框是否是显示的
    * @public
    */
    isSingleAlertShow: function isSingleAlertShow() {
        return this.isShow;
    },


    /**
     * 显示或隐藏单局结算页面
     * @public
     * @param {string} isShow -- 'show'表示显示，'hide'表示隐藏
     */
    singelResultShow: function singelResultShow(isShow) {
        //每一局统计弹出框的显示和隐藏
        this.isShow = isShow === "show" ? true : false;; //设置状态
        this.singleResult.active = this.isShow; //显示弹出框
    },


    /**
     * 显示四个用户的所有信息
     * @public
     * @param {Array} juResults -- 四个用户的所有数据
     * [//每一局的信息，包括4个人的
          {
            outAllCards: ['w3', 't6', 'b', 't3'], //所有出的牌
            outCards: ['w3', 't6', 'b', 't3'] , //剩余出的牌(被别人碰了后，自己出的牌会少一张)
            handCards: ['w3', 't6', 'w6', 'o8'], //手牌
            optMsgs: [...], //操作类型,吃碰杠等的数据
            huaCards: ['i1', 'i3'], //补了的花
            order: 1, //加入房间的顺序，取值：1,2,3,4
            userInfo: { //用户信息
                ready: true, //是否准备好了
                cards: 1000, sex: 1, ip: '192.168.0.196', faceIcon: "5350b36d56b6e054.jpg", isVip: true,
                nickName: "玩家10003", openId: "10003", userId: 10004, state: 0, //0-离线 1-在线
            },
              huInfo:{
                zhuangCount: 3, //庄家总次数  paoCount：2， //点炮总次数
                huCount: 1, //胡牌的总次数  lZCount: 2, //连庄的总次数
                gangFen: 38, //杠分 jingFen: 84, //金分
                //以下四个是自己加的
                daoType: 'sanJingDao',
                huCard: 'w3',
                huFen: 33, //胡分
            }
              currentScore: 39, //上局得分
            roomScore: 99, //自己的总分
            napai: '', //可能分别为 'w5' null, 但是每个人都能看到 'w5'
        },
       ]
         //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
       optMsgs = [
                {p: 289383,  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
                {p: 289383,  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] },//明杠
                {p: 289383,  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] },//暗杠
                {p: 289383,  t: 'buGang', c: 'w3', from: 3892938, m:['w3'], index: 2 },//补杠, 与碰是分开的, index是对应碰的位置
                {p: 289383,  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] },//吃
                  {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
                {p: 289383,  t: 'sanJingDao', c: 'w2', from: 3892938, m:[] }, //三金倒
                {p: 289383,  t: 'youJing', c: 'w2', from: 3892938, m:[] }, //游金
                {p: 289383,  t: 'shuangYou', c: 'w2', from: 3892938, m:[] }, //双游
                {p: 289383,  t: 'sanYou', c: 'w2', from: 3892938, m:[] }, //三游
                {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
                {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
                  ]
         handCards = [ 'e', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2', 't8', 'w2', 't8', 'w2', 'n'] //手牌
       huaCards = ['hj', 'hj', 'hj'] //花牌
     */
    refreshMahResult: function refreshMahResult(juResults) {
        //对数据进行排序，按位置号的大小 升序
        juResults.sort(function (a, b) {
            return room.getPositionOrder(a.userInfo.userId) - room.getPositionOrder(b.userInfo.userId);
        });

        //记录数据
        this.juResults = juResults;

        //显示房号和局数
        this._showRoom_();

        //计算胡牌的类型等 daoType huCard
        this._addHuInfo_();

        //刷新四个用户的显示
        this._setUserInfo_();

        //记录四个人的分数
        for (var i = 0; i < this.juResults.length; i++) {
            this.scores[i] = this.juResults[i].roomScore;
            //将分数存到每个用户的数据中
            room.getUser(i).attr({ 'currentScore': this.juResults[i].currentScore, 'roomScore': this.juResults[i].roomScore });
        }
    },


    /**
     * 计算胡牌的类型等
     */
    _addHuInfo_: function _addHuInfo_() {
        var huInfo = void 0,
            optMsgs = void 0;
        this.juResults.forEach(function (user) {
            huInfo = user.huInfo;
            optMsgs = user.optMsgs;

            huInfo.daoType = '';huInfo.huCard = '';
            TOOL.converOperate(optMsgs); //转换类型
            for (var i = 0; i < optMsgs.length; i++) {
                switch (optMsgs[i].t) {
                    case 'hu':
                    case 'sanJingDao':
                    case 'ziMo':
                    case 'youJing':
                    case 'shuangYou':
                    case 'sanYou':
                    case 'tianHu':
                        huInfo.daoType = optMsgs[i].t;
                        huInfo.huCard = optMsgs[i].c;
                        return;
                }
            }
        });
    },


    /**
     * 获取每个用户的节点
     * @private
     */
    _getItemNodes_: function _getItemNodes_() {
        var _this = this;

        this.itemNodes.length = 0;
        var userNode = null;
        for (var i = 0; i < 4; i++) {
            userNode = cc.find('alertContent/singleBg/users/singleUser' + i, this.singleResult);
            if (!userNode) {
                //如果没有, 就创建一个用户节点
                userNode = cc.instantiate(this.singleUserPref);
                userNode.name = 'singleUser' + i;
                userNode.parent = cc.find('alertContent/singleBg/users', this.singleResult);
            }
            this.itemNodes.push(userNode);
        }

        var mjNode = cc.find('mj', this.itemNodes[0]);
        this.mjArrPosition = mjNode.y; //记录整个麻将节点的原始位置的 y 坐标
        Array.from(mjNode.children).forEach(function (node) {
            return _this.mjPosition.push(node.x);
        }); //记录每个节点的原始位置的 x 坐标
    },


    /**
     * 刷新四个用户的显示
     * @private
     */
    _setUserInfo_: function _setUserInfo_() {
        for (var i = 0; i < 4; i++) {
            this._setUserHeadInfo_(i); //更改头像部分的信息
            this._setMash_(i); //更改一个人的麻将牌和胡牌的类型
            this._setHua_(i); //更改一个人的花牌
            this._setTypeAndScore_(i); //更改 最右边的点炮和分数 还有 中间的胡type
        }
    },


    /**
     * 更改头像部分的信息
     * @private
     * @param {number} k -- 这个人的位置号
     */
    _setUserHeadInfo_: function _setUserHeadInfo_(i) {
        var _ref = [this.juResults[i], this.itemNodes[i].getChildByName('userHead')],
            _ref$ = _ref[0],
            userInfo = _ref$.userInfo,
            huInfo = _ref$.huInfo,
            userHead = _ref[1];


        TOOL.setUserHeadIcon(userHead, userInfo.headIcon); // 更改头像

        // 更改昵称
        userHead.getChildByName('nickName').getComponent(cc.Label).string = userInfo.nickName;

        var user = room.getUser(userInfo.userId);
        var zhuang = user.attr('zhuang');

        // 更改庄家
        userHead.getChildByName('zhuanIcon').active = zhuang;
        //更改房主
        userHead.getChildByName('cap').active = user.attr('isHost');

        var isShowLZ = zhuang && huInfo.lZCount > 0;
        //更改连庄次数
        userHead.getChildByName('lianZhuang').active = isShowLZ;
        isShowLZ && (userHead.getChildByName('lianZhuang').getComponent(cc.Label).string = '连庄: ' + huInfo.lZCount);
    },


    /**
     * 更改一个人的花牌
     * @private
     * @param {number} k -- 这个人的位置号
     */
    _setHua_: function _setHua_(k) {
        var _this2 = this;

        var _ref2 = [this.juResults[k], this.itemNodes[k].getChildByName('hua').children],
            huaCards = _ref2[0].huaCards,
            huaNodes = _ref2[1];


        Array.from(huaNodes).forEach(function (node, index) {
            if (huaCards[index]) {
                node.getComponent(cc.Sprite).spriteFrame = _this2.mj_Atlas.getSpriteFrame('y_' + huaCards[index]);
                node.active = true;
            } else {
                node.active = false;
            }
        });
    },


    /**
     * 更改一个人的麻将牌
     * @private
     * @param {number} k -- 这个人的位置号
     */
    _setMash_: function _setMash_(k) {
        var _this3 = this;

        var _ref3 = [this.itemNodes[k].getChildByName('mj'), this.juResults[k]],
            mj = _ref3[0],
            user = _ref3[1],
            _ref3$ = _ref3[2];
        _ref3$ = _ref3$ === undefined ? user : _ref3$;
        var handCards = _ref3$.handCards,
            optMsgs = _ref3$.optMsgs,
            _ref3$2 = _ref3[3],
            mashs = _ref3$2 === undefined ? mj.children : _ref3$2,
            _ref3$3 = _ref3[4],
            j = _ref3$3 === undefined ? 0 : _ref3$3;


        this._reset_(mj); //重置所有人的牌的显示

        //显示碰、杠的牌
        j = this._setCardsShow_(mashs, optMsgs, j);

        //显示手中的牌
        handCards.forEach(function (cardName) {
            _this3._setMj_(mashs[j], cardName);j++;
        });

        //显示胡的那张牌
        j = this._showHuCard_(mashs, j, k);

        //隐藏多余的(不够14张的, 比如没有胡牌的人)
        for (var i = j; i < mashs.length; i++) {
            mashs[i].active = false;
        }
        // Array.from(mashs).forEach( node => { node.active = false; j++; } );

        //如果上面没有任何信息，麻将牌要向上移动
        if (user.huaCards.length === 0 && !user.userInfo.isZhuang && (user.huInfo.daoType === '' || user.huInfo.daoType === 'dianPao')) {
            mj.y = mj.y + 15;
        } else {
            mj.y = this.mjArrPosition; //恢复原位
        }
    },


    /**
     * 显示胡的那张牌
     * @private
     */
    _showHuCard_: function _showHuCard_(mashs, j, k) {
        if (j > 16) {
            return;
        }

        var huCard = this.juResults[k].huInfo.huCard;

        if (huCard) {
            this._setMj_(mashs[j], huCard); //设置图集
            mashs[j].active = true;
            mashs[j].x = mashs[j].x + 10; //右移一段距离
            j++;
        }
        return j;
    },


    /**
     * 显示碰、杠的牌
     * @private
     * @param {Array} mashs -- 麻将牌的所有节点组成的数组
     * @param {Array} optMsgs -- 操作的牌的数组
    *                   [
                        {p: '289383',  t: 'pong', c: 'w3', from: '3892938', m:['w3', 'w3'] }, //碰
                        {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//明杠
                        {p: '289383',  t: 'anGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//暗杠
                        {p: '289383',  t: 'buGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//补杠
                        {p: '289383',  t: 'chi', c: 'w3', from: '3892938', m:['w3', 'w3'] },//吃
                  ]
     * @param {number} j -- 表示当前已经显示到第几张牌了
     */
    _setCardsShow_: function _setCardsShow_(mashs, optMsgs, j) {
        var _this4 = this;

        var optObj = void 0,
            //第i个操作的对象
        cardName = void 0,
            //牌的名字

        oneOpt = 0,
            //每次的增加量
        optNum = 0; //一共显示了多少个操作，用于计算每个操作之间的间隔

        optMsgs.forEach(function (optObj, index) {
            cardName = optObj.c;oneOpt = 0;
            switch (optObj.t) {
                case 'pong':
                    //碰
                    for (var k = j; k < j + 3; k++) {
                        _this4._setMj_(mashs[k], cardName); //设置图集
                    }
                    j += 3;
                    oneOpt++;
                    break;
                case 'mingGang':
                    //明杠
                    _this4._setMj_(mashs[j], cardName); //设置图集
                    j++;
                    _this4._setMj_(mashs[j], cardName); //设置图集
                    _this4._setMj_(mashs[j].getChildByName('mj'), cardName); //设置图集
                    mashs[j].getChildByName('mj').active = true; //显示
                    j++;
                    _this4._setMj_(mashs[j], cardName); //设置图集
                    j++;
                    oneOpt++;
                    break;
                case 'buGang':
                    //补杠
                    _this4._setMj_(mashs[j], cardName); //设置图集
                    j++;
                    _this4._setMj_(mashs[j], cardName); //设置图集
                    _this4._setMj_(mashs[j].getChildByName('mj'), 'mjB'); //设置图集
                    mashs[j].getChildByName('mj').active = true; //显示
                    j++;
                    _this4._setMj_(mashs[j], cardName); //设置图集
                    j++;
                    oneOpt++;
                    break;
                case 'anGang':
                    //暗杠
                    _this4._setMj_(mashs[j], 'mjB'); //设置图集
                    j++;
                    _this4._setMj_(mashs[j], 'mjB'); //设置图集
                    _this4._setMj_(mashs[j].getChildByName('mj'), cardName); //设置图集
                    mashs[j].getChildByName('mj').active = true; //显示
                    j++;
                    _this4._setMj_(mashs[j], 'mjB'); //设置图集
                    j++;
                    oneOpt++;
                    break;
                case 'chi':
                    //吃
                    optObj.m.forEach(function (card) {
                        _this4._setMj_(mashs[j], card);j++;
                    });
                    oneOpt++;
                    break;
            }

            optNum += oneOpt;
            if (oneOpt) {
                //只有显示增加了，才会整体向后移动
                for (var m = optNum * 3; m < mashs.length; m++) {
                    mashs[m].x = mashs[m].x + 5;
                }
            }
        });

        return j;
    },


    /**
     * 更改麻将牌
     * @private
     * @param {cc.Node} mjNode -- 麻将节点
     * @param {string} cardName -- 牌的名字
     */
    _setMj_: function _setMj_(mjNode, cardName) {
        switch (cardName) {
            case 'mjB':
                //设置为显示背面
                mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardName);
                mjNode.getChildByName('hua').active = false;
                break;
            case 'mjF':
                //设置为显示正面
                mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardName);
                mjNode.getChildByName('hua').active = true;
                break;
            default:
                //设置为显示正面并且显示指定的牌
                mjNode.getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame('mjF');
                mjNode.getChildByName('hua').getComponent(cc.Sprite).spriteFrame = this.mj_Atlas.getSpriteFrame(cardName);
                mjNode.getChildByName('hua').active = true;
        }
    },


    /**
     * 更改 最右边的点炮和分数 还有 中间的胡type
     * @private
     * @param {number} i -- 用户的位置号
     */
    _setTypeAndScore_: function _setTypeAndScore_(i) {
        var right = this.itemNodes[i].getChildByName('right');
        var huInfo = this.juResults[i].huInfo;
        var daoType = huInfo.daoType; //'' 'sanJingDao' 'hu' 'youJing'

        //显示‘到’
        var daoActive = false;
        switch (daoType) {
            case '':
            case 'dianPao':
                daoActive = false;
                break;
            default:
                daoActive = true;
        }
        right.getChildByName('dao').active = daoActive;
        right.getChildByName('type').getComponent(cc.Label).string = this._getHuType_(daoType);
        right.getChildByName('type').active = daoActive;

        var gangFen = huInfo.gangFen,
            //杠分
        jingFen = huInfo.jingFen,
            //金分
        huFen = huInfo.huFen,
            //胡分
        score = void 0;

        //显示 各自的分数
        score = gangFen > 0 ? '+' + gangFen : gangFen;
        right.getChildByName('gang').getComponent(cc.Label).string = '杠分: ' + score; // 更改杠分

        score = jingFen > 0 ? '+' + jingFen : jingFen;
        right.getChildByName('jing').getComponent(cc.Label).string = '金分: ' + score; // 更改金分

        score = huFen > 0 ? '+' + huFen : huFen;
        right.getChildByName('all').getComponent(cc.Label).string = '胡分: ' + score; // 更改胡分

        right.getChildByName('allScore').getComponent(cc.Label).string = this.juResults[i].currentScore; // 更改本局得分
    },


    /**
     * 计算胡牌的更详细的类型 (十三烂、七星十三烂、平胡、七对、碰碰胡、假胡)
     * @private
     * @param {string} type -- 胡牌的类型的字母表示 (thirty、sevenThirty、pingHu、sevenPair、pengPengHu、jiaHu)
     * @returns {string} -- 胡牌的类型的中文表示 (十三烂、七星十三烂、平胡、七对、碰碰胡、假胡)
     */
    _getHuType_: function _getHuType_(type) {
        var huType;
        switch (type) {
            case 'hu':
                huType = '平到';
                break;
            case 'sanJingDao':
                huType = '三金倒';
                break;
            case 'ziMo':
                huType = '自摸';
                break;
            case 'youJing':
                huType = '游金';
                break;
            case 'shuangYou':
                huType = '双游';
                break;
            case 'sanYou':
                huType = '三游';
                break;
            case 'tianHu':
                huType = '天到';
                break;
        }

        return huType;
    },


    /**
     * 重置所有的牌的显示
     * @private
     */
    _reset_: function _reset_(hand) {
        var mashs = hand.children,
            //所有的麻将节点的数组
        upperMj = null; //上面的那张牌

        for (var i = 0; i < mashs.length; i++) {
            //隐藏所有暗杠的上面的牌

            //将上面的麻将隐藏
            upperMj = mashs[i].getChildByName('mj');
            if (upperMj) {
                upperMj.active = false;
            }

            //恢复所有牌的原始位置
            mashs[i].x = this.mjPosition[i];
            mashs[i].active = true;
        }

        this.isShow = false;
    },


    /**
     * 显示房号和局数
     * @private
     */
    _showRoom_: function _showRoom_() {
        //设置房间号
        cc.find('alertContent/singleBg/roomId', this.singleResult).getComponent(cc.Label).string = '房间号: ' + room.roomId;

        var value = room.attr({ 'diFeng': undefined, 'fanXin': undefined }); //底分和番型
        var str = '底:' + value.diFeng + ' 番:' + value.fanXin;
        //设置规则
        cc.find('alertContent/singleBg/method', this.singleResult).getComponent(cc.Label).string = str;

        var allScore = this.juResults[0].currentScore;
        var result = allScore === 0 ? 'draw' : allScore > 0 ? 'win' : 'failure';

        //显示 平局 胜利 失败
        cc.find('alertContent/singleBg/win', this.singleResult).getComponent(cc.Sprite).spriteFrame = this.single_Atlas.getSpriteFrame(result); //draw failure win
    }
});

module.exports = SingleResult;

cc._RFpop();
},{}],"socketLocalMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '01c8f2VT4JO2bVjtVfrRUUa', 'socketLocalMng');
// scripts\common\msgMng\socketLocalMng.js

'use strict';

/**
 * @class
 * @classdesc -- 用于管理发送消息及接收消息的封装
 */
var SocketLocalMng = cc.Class({
    name: 'SocketLocalMng',

    /**
     * @constructor
     * @param {Array} config --
     * {
     *  ID //用于标记是谁发送的消息
     *  procotolObj: //消息的协议对象，定义了发送和接收的消息格式，并可以转换成自己需要的样式
     * }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.ID = config.ID; //用户的唯一标记(身份识别用)
        this.procotolObj = config.procotolObj; //消息的协议对象，定义了发送和接收的消息格式，并可以转换成自己需要的样式

        this.wsObj = null; // socket对象
        this.sendData = { t: '', m: '', p: this.ID }; //要发送的消息格式, 三个字段都有默认值
        this.msgCache = null; //消息缓存

        this.sendDataArr = []; //用于存放要发送，但没发送成功的消息，当socket再次打开时，重新发送
    },

    /**
     * 标记本条消息已经处理完成
     */
    finishMsg: function finishMsg(type) {
        console.log(type + '消息处理完成！');
        this.msgCache.isProcessing = false; //标记已经处理完成
    },

    /**
     * 设置用户的唯一ID
     * @public
     * @param {string} ID -- 用户的唯一ID
     * @returns {this}
     */
    setID: function setID(ID) {
        this.ID = ID;
        return this;
    },

    reset: function reset() {
        this.controllerMap.resetController(); //重置所有的控制器
    },

    /**
     * 设置控制器地图
     * @public
     * @param {object} controllerMap -- 控制器地图
     */
    setControllerMap: function setControllerMap(controllerMap) {
        this.controllerMap = controllerMap;
    },

    /**
     * 打开一个链接
     * @public
     * @param {string} url -- 要连接的url
     * @returns {this}
     */
    openLink: function openLink(url) {

        var self = this;
        setTimeout(function () {
            self.onopen();
        }, 500);

        return this;
    },

    /**
     * 打开成功后的回调
     * @public
     * @param {function} successFunc -- 回调函数
     * @returns {this}
     */
    success: function success(successFunc) {
        var self = this;
        this.onopen = function () {
            //打开成功事件
            console.log("打开成功！");
            self.onmessage = function (event) {
                //接收消息事件
                console.log("客户端--收到一条消息！");
                // var dataObj = JSON.parse(event.data);
                var dataObj = event;
                self.procotolObj.convert(dataObj); //对收到消息进行字段名的转换
                console.log(dataObj);

                self.receiveMessageFunc && typeof self.receiveMessageFunc === 'function' && self.receiveMessageFunc.call(self, dataObj, event);
            };

            successFunc && typeof successFunc === 'function' && successFunc.call(self);

            clientUser && clientUser.combineSend(true); //将发送消息的对象绑定到自己身上

            for (var i = 0; i < self.sendDataArr.length; i++) {
                console.log('要发送的消息：');
                console.log(self.sendDataArr[i]);
                self.send(self.sendDataArr[i]); //发送
            }

            self.sendDataArr.length = 0; //清空队列
        };
        return this;
    },

    /**
     * 收到消息后的回调
     * @public
     * @param {function} receiveMessageFunc -- 回调函数， 自动有参数 dataObj, event
     * @returns {this}
     */
    receiveMessage: function receiveMessage(receiveMessageFunc) {
        this.receiveMessageFunc = receiveMessageFunc;
        return this;
    },

    /**
     * 打开失败后的回调
     * @public
     * @param {function} failureFunc -- 回调函数， 自动有参数  event
     * @returns {this}
     */
    error: function error(failureFunc) {
        var self = this;
        this.onerror = function (event) {
            //打开错误事件
            console.log("连接失败！");

            console.log(event);
            failureFunc && typeof failureFunc === 'function' && failureFunc.call(self, event);
        };
        return this;
    },

    /**
     * 关闭后的回调
     * @public
     * @param {function} closeFunc -- 回调函数
     * @returns {this}
     */
    close: function close(closeFunc) {
        var self = this;
        this.onclose = function () {
            //关闭连接事件
            console.log("已经关闭连接！");
            //自己关闭连接后，要提示自己离线

            if (clientUser && window.room && clientUser.displayHead) {
                clientUser.attr('state', 0);
                clientUser.displayHead('leaveLine', 'show'); //显示用户的离线标记

                //离线后，如果正在解散房间的过程中，就取消解散的流程
                if (room.dismissAlert) {
                    //重置解散弹出框
                    room.dismissAlert.reset();
                    //隐藏解散弹出框
                    room.dismissAlert.dismissShow('hide');
                }

                clientUser.sleep(); //自己不能有任何操作
            }

            closeFunc && typeof closeFunc === 'function' && closeFunc.call(self);
            self.wsObj = undefined;
        };
        return this;
    },

    /**
     * 发送的消息格式: obj = {type:'', id:'', message: ''}
     * 传入的消息格式：obj = {type:'', message: ''} //这个是默认值
     * 对消息格式进行转换，转换后：{t: obj.type | '', p: obj.id | meOpenId, m: obj.message | ''}
     * @public
     * @param {object} dataObj -- 必须是一个对象
     * @returns {this}
     * @example 如果传入的对象中没有type字段，就t='',
     *          如果传入的对象中没有id字段，就 p=this.ID
     *          如果传入的对象中没有message字段，就m='',
     */
    send: function send(dataObj) {
        console.log('要发送的消息：');
        this.sendData.t = dataObj.type === undefined ? '' : this.procotolObj[dataObj.type]; //将字符串表示的类型转换为一个数字
        this.sendData.p = dataObj.openId || this.ID; //发送消息的人的身份
        this.sendData.m = dataObj.message === undefined ? '' : dataObj.message; //消息的实体

        console.log(this.sendData);

        localServer.receive(this.sendData); //发送
        return this;
    }

});

module.exports = SocketLocalMng;

cc._RFpop();
},{}],"socketMng":[function(require,module,exports){
"use strict";
cc._RFpush(module, '31322wzVLRC5ZoIL4X6uneT', 'socketMng');
// scripts\common\msgMng\socketMng.js

'use strict';

/**
 * @class
 * @classdesc -- 用于管理发送消息及接收消息的封装
 */
var SocketMng = cc.Class({
    name: 'SocketMng',

    /**
     * @constructor
     * @param {Array} config --
     * {
     *  ID //用于标记是谁发送的消息
     *  procotolObj: //消息的协议对象，定义了发送和接收的消息格式，并可以转换成自己需要的样式
     * }
     */
    ctor: function ctor() {
        var config = arguments[0];
        this.ID = config.ID; //用户的唯一标记(身份识别用)
        this.procotolObj = config.procotolObj; //消息的协议对象，定义了发送和接收的消息格式，并可以转换成自己需要的样式

        this.wsObj = null; // socket对象
        this.sendData = { t: '', m: '', p: this.ID }; //要发送的消息格式, 三个字段都有默认值
        this.msgCache = null; //消息缓存

        this.sendDataArr = []; //用于存放要发送，但没发送成功的消息，当socket再次打开时，重新发送
    },

    /**
     * 标记本条消息已经处理完成
     */
    finishMsg: function finishMsg(type) {
        console.log(type + '消息处理完成！');
        this.msgCache.isProcessing = false; //标记已经处理完成
    },

    /**
     * 设置用户的唯一ID
     * @public
     * @param {string} ID -- 用户的唯一ID
     * @returns {this}
     */
    setID: function setID(ID) {
        this.ID = ID;
        return this;
    },

    reset: function reset() {
        this.controllerMap.resetController(); //重置所有的控制器
    },

    /**
     * 设置控制器地图
     * @public
     * @param {object} controllerMap -- 控制器地图
     */
    setControllerMap: function setControllerMap(controllerMap) {
        this.controllerMap = controllerMap;
    },

    /**
     * 打开一个链接
     * @public
     * @param {string} url -- 要连接的url
     * @returns {this}
     */
    openLink: function openLink(url) {
        this.wsObj = new WebSocket(url || ''); //创建对象
        return this;
    },

    /**
     * 打开成功后的回调
     * @public
     * @param {function} successFunc -- 回调函数
     * @returns {this}
     */
    success: function success(successFunc) {
        var self = this;
        this.wsObj.onopen = function () {
            //打开成功事件
            console.log("打开成功！");
            successFunc && typeof successFunc === 'function' && successFunc.call(self);

            clientUser && clientUser.combineSend(true); //将发送消息的对象绑定到自己身上

            for (var i = 0; i < self.sendDataArr.length; i++) {
                console.log('缓存中 要发送的消息：');
                var dataObj = self.sendDataArr[i];
                console.log(dataObj);
                this.sendData.t = dataObj.type === undefined ? '' : this.procotolObj[dataObj.type]; //将字符串表示的类型转换为一个数字
                this.sendData.p = this.ID; //发送消息的人的身份
                this.sendData.m = dataObj.message === undefined ? '' : dataObj.message; //消息的实体

                window.pageLog.log('缓存中要发送的' + dataObj.type + '消息：' + JSON.stringify(dataObj));

                self.wsObj.send(JSON.stringify(this.sendData)); //发送
            }

            self.sendDataArr.length = 0; //清空队列

            self.wsObj.onmessage = function (event) {
                //接收消息事件


                var dataObj = JSON.parse(event.data);
                self.procotolObj.convert(dataObj); //对收到消息进行字段名的转换
                if (dataObj.type === 'heart') {
                    console.log("收到心跳消息！");
                } else {
                    console.log("收到一条消息！");
                    console.log(dataObj);
                    window.pageLog.log('收到的' + dataObj.type + '消息：' + JSON.stringify(dataObj));
                }

                self.receiveMessageFunc && typeof self.receiveMessageFunc === 'function' && self.receiveMessageFunc.call(self, dataObj, event);
            };
        };
        return this;
    },

    /**
     * 收到消息后的回调
     * @public
     * @param {function} receiveMessageFunc -- 回调函数， 自动有参数 dataObj, event
     * @returns {this}
     */
    receiveMessage: function receiveMessage(receiveMessageFunc) {
        this.receiveMessageFunc = receiveMessageFunc;
        return this;
    },

    /**
     * 打开失败后的回调
     * @public
     * @param {function} failureFunc -- 回调函数， 自动有参数  event
     * @returns {this}
     */
    error: function error(failureFunc) {
        var self = this;
        this.wsObj.onerror = function (event) {
            //打开错误事件
            console.log("连接失败！");

            console.log(event);
            failureFunc && typeof failureFunc === 'function' && failureFunc.call(self, event);
        };
        return this;
    },

    /**
     * 关闭后的回调
     * @public
     * @param {function} closeFunc -- 回调函数
     * @returns {this}
     */
    close: function close(closeFunc) {
        var self = this;
        this.wsObj.onclose = function () {
            //关闭连接事件
            console.log("已经关闭连接！");
            //自己关闭连接后，要提示自己离线

            if (clientUser && window.room && clientUser.displayHead) {
                clientUser.attr('state', 0);
                clientUser.displayHead('leaveLine', 'show'); //显示用户的离线标记

                //离线后，如果正在解散房间的过程中，就取消解散的流程
                // if(room.dismissAlert){
                //     //重置解散弹出框
                //     room.dismissAlert.reset();
                //     //隐藏解散弹出框
                //     room.dismissAlert.dismissShow('hide');
                // }

                clientUser.sleep(); //自己不能有任何操作
            }

            closeFunc && typeof closeFunc === 'function' && closeFunc.call(self);
            self.wsObj = undefined;
        };
        return this;
    },

    /**
     * 发送的消息格式: obj = {type:'', id:'', message: ''}
     * 传入的消息格式：obj = {type:'', message: ''} //这个是默认值
     * 对消息格式进行转换，转换后：{t: obj.type | '', p: obj.id | meOpenId, m: obj.message | ''}
     * @public
     * @param {object} dataObj -- 必须是一个对象
     * @returns {this}
     * @example 如果传入的对象中没有type字段，就t='',
     *          如果传入的对象中没有id字段，就 p=this.ID
     *          如果传入的对象中没有message字段，就m='',
     */
    send: function send(dataObj) {
        if (dataObj.type === 'heart') {
            console.log('发送了一条心跳消息');
        } else {
            console.log('要发送的消息：');
        }
        this.sendData.t = dataObj.type === undefined ? '' : this.procotolObj[dataObj.type]; //将字符串表示的类型转换为一个数字
        this.sendData.p = this.ID; //发送消息的人的身份
        this.sendData.m = dataObj.message === undefined ? '' : dataObj.message; //消息的实体

        if (dataObj.type !== 'heart') {
            console.log(this.sendData);
            window.pageLog.log('要发送的' + dataObj.type + '消息：' + JSON.stringify(dataObj));
        }

        var sendDataStr = JSON.stringify(this.sendData); //转换成json字符串

        if (!this.wsObj) {
            console.log('send消息时，连接已关闭 ！');
            this.sendDataArr.length = 0; //消息队列先清空
            //如果发送时，socket已经关了，就缓存起来，等下次再连上时，继续发。并且只有下面指定的消息才缓存
            switch (dataObj.type) {
                case 'join':
                case 'create':
                case 'login':
                case 'heart':
                case 'record':
                case 'leaveLine':
                case 'reConnect':
                case 'share':
                case 'face':
                case 'word':
                    break;
                default:
                    window.pageLog.log('要缓存的' + dataObj.type + '消息：' + JSON.stringify(dataObj));
                    this.sendDataArr.push(dataObj);
            }

            if (getGI('model') === 'produce') {
                // window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0c15c83d5c0f91f&redirect_uri=http://www.uitrs.com/Mahjong/tnmj&response_type=code&scope=snsapi_userinfo&state=wx&connect_redirect=1#wechat_redirect';
            } else {
                    // window.location.href = window.location.href;
                }
            return this;
        }

        this.wsObj.send(sendDataStr); //发送
        return this;
    }

});

module.exports = SocketMng;

cc._RFpop();
},{}],"start_controller":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd774bO9SLhMF5LhFTKTAJC6', 'start_controller');
// scripts\common\controller\messageController\start_controller.js

'use strict';

var User = require('user');
var msgComm = require('msgComm');

/**@class
 * @classdesc 开始或结束
 */
var Start_controller = cc.Class({
    name: 'Start_controller',

    /**
     * 开始游戏消息
     * 开始发牌
     * data: {  e | errMsg: [{pong}, {gang}],
                m | message:[
                        {   p: 389292,
                            h: [ {h: 'i2', c: 'i3'}, {h: 'i3', c: 'w2'} ], //没有补花时，为 null
                            c: [ 'i2', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2', 't8', 'w2', 't8', 'w2', 'n'] //玩家拿到的牌
                            gc: 't4', //金牌
                        },
                        {   p: 389292,
                            h: [ {h: 'i2', c: ''}, {h: 'i3', c: ''} ], //没有补花时，为 null
                            c: [ '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''] //玩家拿到的牌
                            gc: 't4', //金牌
                        },
                        ]
                s | success: true, t | type: 1, start
            }
     */
    start_controller: function start_controller(data) {
        console.log('客户端-- 已经收到start消息');

        // clientUser.send( {type:'heart', message: data.t} ); //回复一条确认消息

        room.goldCard = data.message[0].gc; //设置金牌

        //初始化用户数据 添加牌
        this._initUser_(data.message, data.e);

        //初始化房间数据
        this._initRoom_();

        if (getGI('model') !== 'produce') {
            this._devShowCards_(data.e);
        } else {
            //显示每个人的牌
            this._showCards_(data.message);
        }

        //减房卡
        this._subCards_();

        socket.finishMsg('start'); //标记本条消息处理完成
    },

    /**
     * 没有定时器， 直接显示牌
     */
    _devShowCards_: function _devShowCards_(optArr) {
        room.forEach(function (user) {
            user.mahjongMng.mahView.convertHM('hand');
            user.showCards('order');
            room.displayRoomInfo('goldCard', room.goldCard);
            user.showCards('buHua');
        });
        clientUser.showCards('isCanOutCard'); //如果客户是庄家，设置自己能否出牌
        setGI('buHuaOver', true);

        if (optArr) {
            clientUser.showCards('showPanel'); //显示操作
        }
    },

    /**
     * 初始化用户数据
     * @private
     */
    _initUser_: function _initUser_(users, optArr) {
        var user, handCards;
        for (var i = 0; i < users.length; i++) {
            user = room.getUser(users[i].p);

            if (!user.mahjongMng) {
                user.combineMahjong();
            }

            handCards = users[i].c[0] === '' ? users[i].c.length : users[i].c;
            //添加一副牌
            user.setCards({
                handCards: handCards, outCards: null, outAllCards: null, mingCards: null, huaCards: users[i].h
            });

            //隐藏ok
            user.displayHead({ 'ok': 'hide' });
        }

        TOOL.deleteStart(clientUser, optArr); //去掉其中的 开始单游 开始双游 开始三游 并记录下来
        clientUser.attr('operateArr', TOOL.converOperate(optArr)); //存入客户能进行的操作
    },

    /**
     * 显示每个人的牌
     * @private
     */
    _showCards_: function _showCards_() {
        var node = getGI('roomGlobalRes').node;

        var userArr = [];
        var index = room.zhuang;
        for (var i = 0; i < 4; i++) {
            userArr.push(room.getUser(index));
            index--;
            index = index < 0 ? 3 : index;
        }

        var buHuaOnceT = 0.2; //room.attr('buHuaOnceT'); //一次补花间隔 默认为0.8s

        var timeDelay = [];
        for (var i = 0; i < 4; i++) {
            var huaCards = userArr[i].attr('huaCards');
            timeDelay[i] = huaCards ? huaCards.length * buHuaOnceT : 0;
        }

        node.runAction(cc.sequence(cc.callFunc(function () {
            room.forEach(function (user) {
                user.showCards('beiMian');
            });
            room.attr('playingPeriod', 'beiMian'); //背面显示阶段
        }), cc.delayTime(1), cc.callFunc(function () {
            room.forEach(function (user) {
                user.showCards('mix');
            });
            room.attr('playingPeriod', 'mix'); //无序显示阶段
        }), cc.delayTime(1), cc.callFunc(function () {
            room.forEach(function (user) {
                user.showCards('order');
            });
            room.attr('playingPeriod', 'order'); //有序显示阶段
        }), cc.delayTime(1), cc.callFunc(function () {
            room.displayRoomInfo('goldCard', room.goldCard);
            room.attr('playingPeriod', 'gold'); //金牌显示阶段
        }), cc.callFunc(function () {
            userArr[0].showCards('buHua');
            room.attr('playingPeriod', 'buHuaStart'); //补花阶开始
        }), cc.delayTime(timeDelay[0]), cc.callFunc(function () {
            userArr[1].showCards('buHua');
        }), cc.delayTime(timeDelay[1]), cc.callFunc(function () {
            userArr[2].showCards('buHua');
        }), cc.delayTime(timeDelay[2]), cc.callFunc(function () {
            userArr[3].showCards('buHua');
        }), cc.delayTime(timeDelay[3]), cc.callFunc(function () {
            clientUser.showCards('isCanOutCard'); //如果客户是庄家，设置自己能否出牌
            room.attr('playingPeriod', 'buHuaEnd'); //补花阶段结
            setGI('buHuaOver', true);
            clientUser.showCards('showPanel'); //显示操作
        })));
    },

    /**
     * 初始化房间数据
     * @private
     */
    _initRoom_: function _initRoom_() {
        room.attr('period', 'playing');

        //将头像运动到一边去
        room.runHead();
        room.displayRoomInfo({
            playingLayer: 'show', //显示麻将
            timer: 'show', //中间的倒计时框显示
            juCount: { currentJuNum: room.currentJuNum, totalJuNum: room.totalQuan }, //显示当前局数和总局数
            restCardNum: 'show' });

        room.diceToggle('hide'); //隐藏骰子，显示倒计时

        //指向当前用户并倒计时
        room.displayPointer(room.acitveIndex);

        room.setActiveUser(room.acitveIndex); //设置下一个用户

        room.attr('readyArr').length = 0; //清空准备的人的数组

        room.attr('isCanShowAllResult', false); //不可以显示总结算界面
        room.attr('restCardNum', 83); //开局后，剩余牌为83张
    },

    /**
     * 减房卡
     * @private
     */
    _subCards_: function _subCards_() {
        //如果是第一局，并且自己是庄家 就要减去房卡
        if (room.currentJuNum === 1 && room.zhuang === 0) {
            var roomCards = clientUser.attr('roomCards');
            switch (room.totalQuan) {
                case 1:
                case 3:
                    clientUser.attr('roomCards', roomCards - 5);
                    break;
                case 2:
                case 4:
                    clientUser.attr('roomCards', roomCards - 10);
                    break;
            }
        }
    },

    /**
     * 登录
     * 登录后，会返回登录的用户的基本信息
     * data: {  e | errMsg: null,
                m | message: {cards: 1000//房卡数, faceIcon:"5350b36d56b6e054.jpg"//头像, nickName: "玩家10004"//昵称,
                        openId:"10004", userId:10004, sex: 1, ip: '192.168.0.101' },
                s | success: true, t | type: 4, login
            }
    */
    login_controller: function login_controller(data) {
        console.log('客户端-- 执行 login_controller...');

        var userInfo = data.message; //用户信息
        msgComm.initHall(userInfo); //初始始化 客户对象 及 大厅页面

        socket.finishMsg('login'); //标记本条消息处理完成
    }

});

module.exports = Start_controller;

cc._RFpop();
},{"msgComm":"msgComm","user":"user"}],"start_serverController":[function(require,module,exports){
"use strict";
cc._RFpush(module, '933c5tIKyhI6ZUrgRL+Cq9B', 'start_serverController');
// scripts\server\serverController\start_serverController.js

'use strict';

var rebuildMsgMng = require('rebuildMsgMng');

/**@class
 * @classdesc 开始或结束
 */
var Start_serverController = cc.Class({
    name: 'Start_serverController',

    /**
     * 开始游戏消息
     * 开始发牌
     * data: {  e | errMsg: null,
                m | message: [ 'e', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2', 't8', 'w2', 't8', 'w2', 'n'] //玩家拿到的牌
                s | success: true, t | type: 1, start
            }
     */
    start_controller: function start_controller(data) {
        console.log('服务器--已经收到start消息');
    },

    /**
     * 登录
     * 登录后，会返回登录的用户的基本信息
    */
    login_controller: function login_controller(data) {
        console.log('服务器--执行 login_controller...');

        localServer.send({ type: 'login', //login
            message: window.testData.test_users[0]
        });

        // setTimeout(function(){
        //     rebuildMsgMng.sendRebuildMsg(); //发送重构消息
        // }, 1000);
    }

});

module.exports = Start_serverController;

cc._RFpop();
},{"rebuildMsgMng":"rebuildMsgMng"}],"test_allResultData":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7ad66PLMENAJrRFGBaXkETh', 'test_allResultData');
// scripts\common\testData\test_allResultData.js

'use strict';

//每八局的信息，包括4个人的

var users = require('test_createUser');
//每一局的信息，包括4个人的
var allResultData = [{
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [{ p: '289383', t: 'pong', c: 'w2', from: '3892938', m: ['w2', 'w2'] }, //碰
    { p: '289383', t: 'mingGang', c: 'w3', from: '3892938', m: ['w3', 'w3', 'w3'] }, //明杠
    { p: '289383', t: 'anGang', c: 'i2', from: '3892938', m: ['i2', 'i2', 'i2'] }, //暗杠
    { p: '289383', t: 'buGang', c: 'o5', from: '3892938', m: ['o5', 'o5', 'o5'] }, //补杠
    { p: '289383', t: 'chi', c: 't3', from: '3892938', m: ['t4', 't5', 't3'] }],
    //手牌
    handCards: ['j2'],
    //花牌
    huaCards: ['h2', 'i1', 'h1', 'i3', 'h4'],
    userInfo: users[0],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 38, //杠分
        jingFen: 14, //金分
        huFen: 2 },
    //房间及个人信息
    currentScore: 10, roomScore: -20
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [],
    //手牌
    handCards: ['j1', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 'j2', 'w2', 't8', 'w2', 't8', 'w2', 'f1', 'o2'],
    //花牌
    huaCards: [],
    userInfo: users[1],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 0, //杠分
        jingFen: 33, //金分
        huFen: 2 },
    //房间及个人信息
    currentScore: 28, roomScore: -19
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [
    // {p: '289383',  t: 'pong', c: 'w2', from: '3892938', m:['w2', 'w2'] }, //碰
    // {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//明杠
    { p: 289383, t: 'youJing', c: 'w2', from: 3892938, m: [] }],
    //手牌
    handCards: ['j2', 'w2', 'o2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 'f3', 'w2', 't8', 'w2', 't8', 'w2', 'i3'],
    //花牌
    huaCards: ['h3', 'i2', 'h3'],
    userInfo: users[2],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 11, //杠分
        jingFen: 14, //金分
        huFen: 2 },
    //房间及个人信息
    currentScore: 4, roomScore: -14
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [
        // {p: '289383',  t: 'pong', c: 'w2', from: '3892938', m:['w2', 'w2'] }, //碰
        // {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//明杠
    ],
    //手牌
    handCards: ['j1', 'w2', 't8', 'o2', 'w4', 't8', 'w2', 't8', 'w2', 'f2', 'w2', 't8', 'w2', 't8', 'w2', 'h1'],
    //花牌
    huaCards: ['h1', 'h3'],
    userInfo: users[3],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 8, //杠分
        jingFen: 2, //金分
        huFen: 2 },
    //房间及个人信息
    currentScore: 5, roomScore: -23
}];

module.exports = allResultData;

/**麻将编码
 * 红花: 'i1', 'i2', 'i3', 'i4' -- 春夏秋冬
 * 黑花: 'h1', 'h2', 'h3', 'h4' -- 梅兰竹菊
 * 风牌: 'f1', 'f2', 'f3', 'f4' -- 东南西北
 * 箭牌: 'j1', 'j2', 'j3' -- 中发白
 * 序数牌: o1~o9 -- 筒牌  t1~t9 -- 条牌  w1~w9 -- 万牌
 */
/**
        {p: '289383',  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
        {p: '289383',  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] }, //明杠
        {p: '289383',  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] }, //暗杠
        {p: '289383',  t: 'buGang', c: 'w3', from: 3892938, m:['w3'] }, //补杠, 与碰是分开的
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w3', 'w4', 'w2'] }, //吃
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3', 'w2'] }, //吃

        {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
        {p: 289383,  t: 'sanJingDao', c: 'w2', from: 3892938, m:[] }, //三金倒
        {p: 289383,  t: 'youJing', c: 'w2', from: 3892938, m:[] }, //游金
        {p: 289383,  t: 'shuangYou', c: 'w2', from: 3892938, m:[] }, //双游
        {p: 289383,  t: 'sanYou', c: 'w2', from: 3892938, m:[] }, //三游
        {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
        {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
 */
/**

最终需要的格式：
[
    {userId: 389233, zhuangNum: 1, huPaiNum: 3, dianPaoNum: 2, scores: 8, nickName: '我是昵称121', headIcon:'389.png'},
    {userId: 389233, zhuangNum: 1, huPaiNum: 3, dianPaoNum: 2, scores: 8, nickName: '我是昵称121', headIcon:'389.png'},
    {userId: 389233, zhuangNum: 1, huPaiNum: 3, dianPaoNum: 2, scores: 8, nickName: '我是昵称121', headIcon:'389.png'},
    {userId: 389233, zhuangNum: 1, huPaiNum: 3, dianPaoNum: 2, scores: 8, nickName: '我是昵称121', headIcon:'389.png'},
]





 */

cc._RFpop();
},{"test_createUser":"test_createUser"}],"test_cardsData":[function(require,module,exports){
"use strict";
cc._RFpush(module, '09764SMcmlB6YVl7Hje43hF', 'test_cardsData');
// scripts\common\testData\test_cardsData.js

'use strict';

//4个人的牌
var cardsData = [{
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [{ p: '10000', t: 'pong', c: 'w2', from: '3892938', m: ['w2', 'w2'] }, //碰
    { p: '10000', t: 'mingGang', c: 'w3', from: '3892938', m: ['w3', 'w3', 'w3'] }, //明杠
    { p: '10000', t: 'anGang', c: 'i2', from: '3892938', m: ['i2', 'i2', 'i2'] }, //暗杠
    { p: '10000', t: 'anGang', c: 't4', from: '3892938', m: ['t4', 't4', 't4'] }, //暗杠
    { p: '10000', t: 'buGang', c: 'w2', from: '3892938', m: [], index: 0 }, //补杠
    { p: '10000', t: 'chi', c: 't3', from: '3892938', m: ['t4', 't5'] }],
    //手牌
    handCards: ['w2', 'w2', 'w2', 'w2', 'w1', 'w1', 'w1', 'w1', 't2', 't3', 't4', 't5', 'w3', 'w3', 'w3', 't9', 't9'],
    hua: [{ h: 'i2', c: 'i3' }, { h: 'i3', c: 'w2' }],
    // handCards: 1,
    //花牌
    huaCards: ['h2', 'i1', 'h1', 'i3', 'h4'],
    //所有出的牌
    outCards: ['t2', 'f1', 'w2', 't7', 'i2', 't3', 'w2', 't1', 'w3', 'w2', 'w7', 'i1', 't3', 'w2', 'w2', 'w7', 'h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7', 'i1'],
    //剩余出的牌(被别人碰了后，自己出的牌会少一张)
    outAllCards: ['t2', 'f1', 'w2', 't7', 'i2', 't3', 'w2', 't1', 'w3', 'w2', 'w7', 'i1', 't3', 'w2', 'w2', 'w7', 'h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7', 'i1'],
    //要拿的牌
    naPaiCards: ['h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7']
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [{ p: '10001', t: 'pong', c: 'w2', from: '3892938', m: ['w2', 'w2'] }, //碰
    { p: '10001', t: 'mingGang', c: 'w3', from: '3892938', m: ['w3', 'w3', 'w3'] }, //明杠
    { p: '10001', t: 'anGang', c: 'i2', from: '3892938', m: ['i2', 'i2', 'i2'] }, //暗杠
    { p: '10000', t: 'buGang', c: 'w2', from: '3892938', m: [], index: 0 }],
    //手牌
    handCards: ['i1', 'o8', 'w2', 'o5', 't8', 'w2', //  't8', 'w2', 'j2',    'i2', 'f1', 'i1',   'o1', 'o2', 'o3',
    'i3'],
    hua: [{ h: 'i1', c: '' }, { h: 'i3', c: '' }],
    // handCards: 7,
    //花牌
    huaCards: ['h2', 'i1', 'h1', 'i3', 'h4'],
    //出的牌
    outCards: ['t7', 't3', 'w2', 'w7', 'i1', 't3'],
    //剩余出的牌(被别人碰了后，自己出的牌会少一张)
    outAllCards: ['t2', 'f1', 'w2', 't7', 'i2', 't3', 'w2', 't1', 'w3', 'w2', 'w7', 'i1', 't3', 'w2', 'w2', 'w7', 'h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7', 'i1'],
    //要拿的牌
    naPaiCards: ['h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7']
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [{ p: '10002', t: 'pong', c: 'w2', from: '3892938', m: ['w2', 'w2'] }, //碰
    { p: '10002', t: 'mingGang', c: 'w3', from: '3892938', m: ['w3', 'w3', 'w3'] }, //明杠
    { p: '10002', t: 'anGang', c: 'i2', from: '3892938', m: ['i2', 'i2', 'i2'] }, //暗杠
    { p: '10000', t: 'buGang', c: 'w2', from: '3892938', m: [], index: 0 }],
    //手牌
    handCards: ['j2', 't8', 'i2', 'o5', 't8', 'w2', //  't8', 'w2', 'j2',    'i2', 'f1', 'i1',   'o1', 'o2', 'o3',
    't1'],
    hua: [{ h: 'i2', c: 'i3' }, { h: 'i3', c: '' }],
    // handCards: 7,
    //花牌
    huaCards: ['h2', 'i1', 'h1', 'i3', 'h4'],
    //出的牌
    outCards: ['w9', 't1', 'w2', 'w7', 'i1', 't3', 'w2'],
    //剩余出的牌(被别人碰了后，自己出的牌会少一张)
    outAllCards: ['t2', 'f1', 'w2', 't7', 'i2', 't3', 'w2', 't1', 'w3', 'w2', 'w7', 'i1', 't3', 'w2', 'w2', 'w7', 'h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7', 'i1'],
    //要拿的牌
    naPaiCards: ['h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7']
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [{ p: '10003', t: 'pong', c: 'w2', from: '3892938', m: ['w2', 'w2'] }, //碰
    { p: '10003', t: 'mingGang', c: 'w3', from: '3892938', m: ['w3', 'w3', 'w3'] }, //明杠
    { p: '10003', t: 'anGang', c: 'i2', from: '3892938', m: ['i2', 'i2', 'i2'] }, //暗杠
    { p: '10000', t: 'buGang', c: 'w2', from: '3892938', m: [], index: 0 }],
    //手牌
    handCards: ['i2', 'i3', 'w2', 'o5', 't8', 'w2', //  't8', 'w2', 'j2',    'i2', 'f1', 'i1',   'o1', 'o2', 'o3',
    't1'],
    hua: [{ h: 'i2', c: '' }, { h: 'i3', c: '' }],
    // handCards: 7,
    //花牌
    huaCards: ['h2', 'i1', 'h1', 'i3', 'h4'],
    //出的牌
    outCards: ['f3', 't2', 'w6', 'w7', 'i1', 't3', 'w2', 'h1', 'o5', 'w2', 'w7', 'i1', 't3', 'w2', 'w2', 'w7'],
    //剩余出的牌(被别人碰了后，自己出的牌会少一张)
    outAllCards: ['t2', 'f1', 'w2', 't7', 'i2', 't3', 'w2', 't1', 'w3', 'w2', 'w7', 'i1', 't3', 'w2', 'w2', 'w7', 'h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7', 'i1'],
    //要拿的牌
    naPaiCards: ['h1', 't3', 'w2', 'w7', 'i1', 't3', 'w2', 'i1', 't3', 'w2', 'w7']
}];

module.exports = cardsData;

/**麻将编码
 * 红花: 'i1', 'i2', 'i3', 'i4' -- 春夏秋冬
 * 黑花: 'h1', 'h2', 'h3', 'h4' -- 梅兰竹菊
 * 风牌: 'f1', 'f2', 'f3', 'f4' -- 东南西北
 * 箭牌: 'j1', 'j2', 'j3' -- 中发白
 * 序数牌: o1~o9 -- 筒牌  t1~t9 -- 条牌  w1~w9 -- 万牌
 */

cc._RFpop();
},{}],"test_createRoom":[function(require,module,exports){
"use strict";
cc._RFpush(module, '42828Gs5b9JSbWlB0sz82dW', 'test_createRoom');
// scripts\common\testData\test_createRoom.js

"use strict";

//所有的测试数据

var roomData = {
    roomId: 0, //房间号
    totalJuNum: 0, //总局数
    currentJuNum: 0, //当前局数--每一局更新
    totalQuan: 0, //总圈数
    currentQuan: 0, //当前圈数
    usersArr: [], //存放加入的所有用户，是以join顺序来存的
    acitveIndex: 0, //当前活动用户的位置号
    openIdOrder: null, //{openId1: user1, openId2: user2}
    zhuang: 0 };

cc._RFpop();
},{}],"test_createUser":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a4209B16D5B66evbC9GWCEh', 'test_createUser');
// scripts\common\testData\test_createUser.js

"use strict";

var _ref;

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

/**
 * sex //1表示男 2表示女
 * state: 1, //0-离线 1-在线
 */
var users = [(_ref = { cards: 1000, faceIcon: "5350b36d56b6e054.jpg", nickName: "玩家10101", openId: "10101", userId: 10101,
  isVip: true, sex: 1, ip: '192.168.0.101' }, _defineProperty(_ref, "isVip", true), _defineProperty(_ref, "state", 1), _defineProperty(_ref, "ready", true), _defineProperty(_ref, "isHost", false), _ref), { cards: 1001, faceIcon: "5350b36d56b6e054.jpg", nickName: "玩家10102", openId: "10102", userId: 10102,
  isVip: true, sex: 1, ip: '192.168.0.104', roomId: '', state: 1, ready: true, isHost: false
}, { cards: 1002, faceIcon: "5350b36d56b6e054.jpg", nickName: "玩家10103", openId: "10103", userId: 10103,
  isVip: true, sex: 1, ip: '192.168.0.102', roomId: '', state: 1, ready: true, isHost: true
}, { cards: 3003, faceIcon: "5350b36d56b6e054.jpg", nickName: "玩家10104", openId: "10004", userId: 10004,
  isVip: true, sex: 1, ip: '192.168.0.106', roomId: '', state: 1, ready: true, isHost: false
}];

module.exports = users;

cc._RFpop();
},{}],"test_data":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0c2ceb9daRLE4VZygl0he/E', 'test_data');
// scripts\common\testData\test_data.js

'use strict';

//所有的测试数据

var testData = {
    test_cardsData: require('test_cardsData'),
    test_users: require('test_createUser'),
    test_allResultData: require('test_allResultData'),
    test_singleResultData: require('test_singleResultData')
};

module.exports = testData;

/**麻将编码
 * 红花: 'i1', 'i2', 'i3', 'i4' -- 春夏秋冬
 * 黑花: 'h1', 'h2', 'h3', 'h4' -- 梅兰竹菊
 * 风牌: 'f1', 'f2', 'f3', 'f4' -- 东南西北
 * 箭牌: 'j1', 'j2', 'j3' -- 中发白
 * 序数牌: o1~o9 -- 筒牌  t1~t9 -- 条牌  w1~w9 -- 万牌
 */

cc._RFpop();
},{"test_allResultData":"test_allResultData","test_cardsData":"test_cardsData","test_createUser":"test_createUser","test_singleResultData":"test_singleResultData"}],"test_singleResultData":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'dea51bzo/FFl6hzNgrDcUub', 'test_singleResultData');
// scripts\common\testData\test_singleResultData.js

'use strict';

var users = require('test_createUser');
//每一局的信息，包括4个人的
var singleResultData = [{
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [{ p: '289383', t: 'pong', c: 'w2', from: '3892938', m: ['w2', 'w2'] }, //碰
    { p: '289383', t: 'mingGang', c: 'w3', from: '3892938', m: ['w3', 'w3', 'w3'] }, //明杠
    { p: '289383', t: 'anGang', c: 'i2', from: '3892938', m: ['i2', 'i2', 'i2'] }, //暗杠
    { p: '289383', t: 'buGang', c: 'o5', from: '3892938', m: ['o5', 'o5', 'o5'] }, //补杠
    { p: '289383', t: 'chi', c: 't3', from: '3892938', m: ['t4', 't5', 't3'] }],
    //手牌
    handCards: ['j2'],
    //花牌
    huaCards: ['h2', 'i1', 'h1', 'i3', 'h4'],
    userInfo: users[0],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 38, //杠分
        jingFen: 14 },
    //房间及个人信息
    currentScore: 10, roomScore: -20
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [],
    //手牌
    handCards: ['j1', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 'j2', 'w2', 't8', 'w2', 't8', 'w2', 'f1', 'o2'],
    //花牌
    huaCards: [],
    userInfo: users[1],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 0, //杠分
        jingFen: 33 },
    //房间及个人信息
    currentScore: 28, roomScore: -19
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [
    // {p: '289383',  t: 'pong', c: 'w2', from: '3892938', m:['w2', 'w2'] }, //碰
    // {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//明杠
    { p: 289383, t: 'youJing', c: 'w2', from: 3892938, m: [] }],
    //手牌
    handCards: ['j2', 'w2', 'o2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 'f3', 'w2', 't8', 'w2', 't8', 'w2', 'i3'],
    //花牌
    huaCards: ['h3', 'i2', 'h3'],
    userInfo: users[2],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 11, //杠分
        jingFen: 14 },
    //房间及个人信息
    currentScore: 4, roomScore: -14
}, {
    //p-操作者String t-操作类型int c-操作牌String from-触发者String m-涉及牌String[]
    //t-- 'pong' 'mingGang'  'anGang' 'buGang' 'chi'
    optMsgs: [
        // {p: '289383',  t: 'pong', c: 'w2', from: '3892938', m:['w2', 'w2'] }, //碰
        // {p: '289383',  t: 'mingGang', c: 'w3', from: '3892938', m:['w3', 'w3'] },//明杠
    ],
    //手牌
    handCards: ['j1', 'w2', 't8', 'o2', 'w4', 't8', 'w2', 't8', 'w2', 'f2', 'w2', 't8', 'w2', 't8', 'w2', 'h1'],
    //花牌
    huaCards: ['h1', 'h3'],
    userInfo: users[3],
    //胡牌相关
    huInfo: {
        zhuangCount: 3, //庄家总次数
        paoCount: 2, //点炮总次数
        huCount: 1, //胡牌的总次数
        lZCount: 2, //连庄的总次数
        gangFen: 8, //杠分
        jingFen: 2 },
    //房间及个人信息
    currentScore: 5, roomScore: -23
}];

module.exports = singleResultData;

/**麻将编码
 * 红花: 'i1', 'i2', 'i3', 'i4' -- 春夏秋冬
 * 黑花: 'h1', 'h2', 'h3', 'h4' -- 梅兰竹菊
 * 风牌: 'f1', 'f2', 'f3', 'f4' -- 东南西北
 * 箭牌: 'j1', 'j2', 'j3' -- 中发白
 * 序数牌: o1~o9 -- 筒牌  t1~t9 -- 条牌  w1~w9 -- 万牌
 */
/**
        {p: '289383',  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
        {p: '289383',  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] }, //明杠
        {p: '289383',  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] }, //暗杠
        {p: '289383',  t: 'buGang', c: 'w3', from: 3892938, m:['w3'] }, //补杠, 与碰是分开的
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w3', 'w4', 'w2'] }, //吃
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3', 'w2'] }, //吃

        {p: 289383,  t: 'hu', c: 'w2', from: 3892938, m:[] }, //点炮胡
        {p: 289383,  t: 'sanJingDao', c: 'w2', from: 3892938, m:[] }, //三金倒
        {p: 289383,  t: 'youJing', c: 'w2', from: 3892938, m:[] }, //游金
        {p: 289383,  t: 'shuangYou', c: 'w2', from: 3892938, m:[] }, //双游
        {p: 289383,  t: 'sanYou', c: 'w2', from: 3892938, m:[] }, //三游
        {p: 289383,  t: 'tianHu', c: 'w2', from: 3892938, m:[] }, //天胡
        {p: 289383,  t: 'ziMo', c: 'w2', from: 3892938, m:[] }, //自摸胡
 */

cc._RFpop();
},{"test_createUser":"test_createUser"}],"tools":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'af0c8X9T4NH5Z4x6IBi8c8h', 'tools');
// scripts\common\tools.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var SendMessageMng = require('socketMng');
var ControllerMap = require('controllerMapMng');
var MsgCache = require('msgCache');

var TOOL = {
    data: {},

    /**
     * 重置数据
     * @public
     */
    resetData: function resetData() {},

    /**
     * 复制一个一维数组中的值到另一个数组中去(改变原数组)
     * @public
     * @param {Array}  arrS -- 数据来源
     * @param {Array}  arrD -- 要增加数据的数组
     */
    copyArray: function copyArray(arrD, arrS) {
        for (var i = 0; i < arrS.length; i++) {
            arrD.push(arrS[i]);
        }
    },

    /**返回一个新数组，去掉了原来 数组中间及两边 的0元素，专用于 风牌、箭牌
    * ，注意其返回值是经过复制的，不影响传入的参数
    * @param {Array} arr -- 一个一维数组，表示牌的张数, 不会改变arr的值
    * @returns {Array} -- 当传入 [0, 0, 1, 0, 3, 2, 0, 0 ] ==》 [1, 3, 2]并具有属性： .index0 = 2, .index1 = 4, .index2 = 5
    *                     注意,在降级时也会调用这个函数，这时传入的参数arr [0, 0, 1] 并具有属性： .index0 = 2, .index1 = 4, .index2 = 5
    *                          这时，必须根据传入的index0或index1来设置新的index
    */
    getNoZeroArr: function getNoZeroArr(arr) {
        var noZeroArr = [];
        arr.forEach(function (value, index) {
            if (value !== 0) {
                noZeroArr.push(value);
                if (arr[index] !== undefined) {
                    //如果传入的数组中已经有索引了，说明是降级时调用的
                    noZeroArr['index' + (noZeroArr.length - 1)] = arr[index]; //记录每张牌的索引 index0: 3
                } else {
                    //否则，是第一次调用
                    noZeroArr['index' + (noZeroArr.length - 1)] = index; //记录每张牌的索引 index0: 3
                }
            }
        });
        return noZeroArr;
    },

    /** 传入时 arr = [0, 2,1,2,0, 0, 1,1,1,2] ，注意其返回值是经过复制的，不影响传入的参数
     * @param {Array} arr -- 一个一维数组，表示牌的张数, 不会改变arr的值
     *  @returns {Array} -- 当传入 [0, 2,1,2,0, 0, 1,1,1,2] ==》 [ [2,1,2], [1,1,1,2] ]，这个数组中每个元素都具有属性： .index0 = 2, .index1 = 4, .index2 = 5
     *                      当传入 [1,1,1,2] ==》 [ [1,1,1,2] ]，这个数组中每个元素都具有属性： .index0 = 2, .index1 = 4, .index2 = 5
     *                      当传入 [0,0,0,0,0] ==》 []， 这个数组中没有元素
     */
    getSplitAll: function getSplitAll(arr) {
        //将1-9这几个数字按是否相连进行切分，专用于 序数牌
        var splitAll = [],
            split = null,
            state = false;
        arr.forEach(function (value, index) {
            if (value !== 0) {
                if (!state) {
                    state = true;
                    split = [];
                    splitAll.push(split);
                }
                split.push(value);
                split['index' + (split.length - 1)] = index; //记录每张牌的索引 index0: 3
            } else {
                state = false;
            }
        });
        //console.log("数组：", arr, " 的分割结果为：" , splitAll);
        return splitAll;
    },

    /**操作原数组， 去掉数组 前面 的所有0元素，专用于 序数牌
     * 当传入 [0, 0, 1, 2, 0, 1, 0, 0, 2, 0] ==》 [1, 2, 0, 1, 0, 0, 2, 0]
     * @param {Array} arr -- 一个一维数组，表示牌的张数, 一定会改变arr的值
     *                          注意,在降级时才会调用这个函数，这时传入的参数arr [0, 0, 1, 0] 并具有属性： .index0 = 2, .index1 = 4, .index2 = 5, .index3=8
     *                             这时，必须根据传入的index0或index1来设置新的index
     */
    trimFrontZero: function trimFrontZero(arr) {
        var num = -1;
        while (arr[0] === 0) {
            arr.shift();
            num++;
        }
        if (num > -1 && arr.index0 !== undefined) {
            //只有真正去掉了一些0元素，并且arr本身就有index0这样的属性，才进行属性更新
            for (var i = 0; i < arr.length; i++) {
                arr['index' + i] = arr["index" + num];
            }
        }
    },

    isZeroArr: function isZeroArr(arr) {
        //判断一个数组是否全为0，返回true则全是0
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] !== 0) {
                return false;
            }
        }
        return true;
    },
    copyArr: function copyArr(originalArr, destArr) {
        for (var i = 0; i < originalArr.length; i++) {
            destArr[i] = originalArr[i];
        }
        destArr.length = originalArr.length;
    },

    /**
     * 将形如 name1=value1&name2=value2的字符串变成一个对象
     * @public
     * @param {string} str --  形如 name1=value1&name2=value2 的字符串
     * @returns {object} -- {name1: value1, name2: value2}
     */
    convertParamToObj: function convertParamToObj(str) {
        var result = {};
        var arr = str.split('&');
        for (var i = 0; i < arr.length; i++) {
            var keyValArr = arr[i].split('=');
            result[keyValArr[0]] = keyValArr[1];
        }
        return result;
    },

    /**
     * 将形如 {name1: value1, name2: value2}的对象转换成 name1=value1&name2=value2  的字符串
     * @public
     * @param {object} obj --  {name1: value1, name2: value2}
     * @returns {string} -- 形如 name1=value1&name2=value2 的字符串
     */
    convertObjToParam: function convertObjToParam(obj) {
        var arr = [];
        for (var prop in obj) {
            arr.push(prop + '=' + obj[prop]);
        }
        return arr.join('&');
    },

    /**
     * 自动设置默认头像
     * @public
     * @param {cc.Node} node --  必须，要设置的头像图片挂载的节点
     * @param {string} value --  必须，取值 url 必须是同域的(http://www.uitrs.com)
     *                           如： url = 'http://www.uitrs.com/UExpress/static/udsapp/img/2017020979539452.png';
     */
    setUserHeadIcon: function setUserHeadIcon(node, value) {
        var iconName;
        switch (value) {
            case 'default':
                iconName = 'noHead';
                break;
            default:
                //如果不是生产环境
                if (getGI('model') !== 'produce') {
                    iconName = 'noHead';
                } else {
                    var url = value;
                    // url = 'http://120.24.57.27:8080/Mahjong/img/faceicons/518034.jpg';
                    cc.loader.load(url, function (err, texture) {
                        var frame = new cc.SpriteFrame(texture);
                        node.getComponent(cc.Sprite).spriteFrame = frame;
                        node.width = 65;
                        node.height = 65;
                    });
                }
        }

        if (iconName) {
            var roomGlobalRes = getGI('roomGlobalRes');
            var roomMain_Atlas = roomGlobalRes && roomGlobalRes.roomMain_Atlas;
            if (roomMain_Atlas) {
                node.getComponent(cc.Sprite).spriteFrame = roomMain_Atlas.getSpriteFrame(iconName);
            }
        }
    },

    /**
     * 更改微信配置对象中的link中的state=wx <==> state=389238  即，是不是加上房间号
     * @public
     */
    replaceShareUrl: function replaceShareUrl(value) {
        if (getGI('model') !== 'produce') {
            return;
        }

        var shareConfig = getGI('shareConfig');

        if (!shareConfig) {
            this.timer1 = setTimeout(function () {
                TOOL.replaceShareUrl(value);
            }, 500);
            return;
        }

        clearTimeout(this.timer1);

        var desc = '咱泉洲人自己的麻将 ';

        if (value === 'wx') {
            shareConfig.link = shareConfig.link.replace(/\?roomId=\d{6}/, '');
            shareConfig.title = '【' + clientUser.attr('nickName') + '】邀请你一起来玩 【泉洲麻将】';
            shareConfig.desc = desc;
        } else if (typeof value === 'number' || typeof value === 'string') {
            shareConfig.link = shareConfig.link.replace(/\?roomId=\d{6}/, '');
            shareConfig.link = shareConfig.link + '?roomId=' + value;
            shareConfig.title = '【' + clientUser.attr('nickName') + '】邀请你加入房间：' + room.roomId + ' - 【泉洲麻将】';
            shareConfig.desc = desc + this._getArithmeticStr_();
        } else if ((typeof value === 'undefined' ? 'undefined' : _typeof(value)) === 'object') {
            //[{name: '小古河记', score: 33}, {name: '白开水', score: -102}]

            value.sort(function (a, b) {
                return b.score - a.score; //由大到小的顺
            });

            //可能有多个大赢家(分数相同)
            var contentStr = '',
                titleStr = '大赢家-';
            var maxScore = value[0].score;
            maxScore = maxScore > 0 ? '+' + maxScore : maxScore; //加上'+'号
            for (var i = 0; i < value.length; i++) {
                var obj = value[i];
                obj.score = obj.score > 0 ? '+' + obj.score : obj.score; //加上'+'号
                if (obj.score === maxScore) {
                    titleStr += obj.name + ':' + maxScore + '分 '; //'大赢家-:小强+18分 小兵+18分 '
                } else {
                    contentStr += obj.name + ':' + obj.score + '分 '; // '小古河记:+4分 黄斯基: -77分 白开水: -102分 '
                }
            }

            shareConfig.title = titleStr;
            shareConfig.desc = contentStr + this._getArithmeticStr_();
            shareConfig.link = shareConfig.link.replace(/\?roomId=\d{6}/, '');
        } else {
            shareConfig.title = '【' + clientUser.attr('nickName') + '】邀请你一起来玩 【泉洲麻将】';
            shareConfig.desc = desc;
        }
    },

    _getArithmeticStr_: function _getArithmeticStr_() {
        var value = room.attr({ 'ju': undefined, 'diFeng': undefined, 'fanXin': undefined }); //底分和番型
        return '房间：' + value.ju + ' 底:' + value.diFeng + ' 限制:' + value.fanXin; //房间：100分 底: 8分 限制: 双金不能平到
    },


    /**
     * 创建并配置一个socket
     * @private
     */
    createSocket: function createSocket(firstMsg, reLink) {
        var socketURL = getGI('socketURL');

        if (socketURL === 'localhost') {
            SendMessageMng = require('socketLocalMng');
        }

        if (reLink !== 'reLink' || !window.socket) {
            //如果不是重连
            //创建socket管理对象SendMessageMngLocal
            window.socket = new SendMessageMng({
                ID: 0, //用户的唯一标志
                procotolObj: window.messageType });

            window.socket.setControllerMap( //创建调用控制器的对象
            new ControllerMap({
                controllerMap: window.controllerMap }));
            window.msgCache = new MsgCache(window.socket.controllerMap);
            window.socket.msgCache = window.msgCache;
        }

        //创建socket对象
        window.socket.openLink(socketURL) //打开一个链接
        .success(function () {
            //打开成功
            // getGI('offlineAlert').showOfflineAlert('hide'); //隐藏离线提示弹出框
            this.send(firstMsg); //发送登录消息
        }).receiveMessage(function (data, event) {
            //接收消息， this 是指 socket对象
            this.msgCache.pushMsg(data);
        }).error(function () {
            //打开失败
            getGI('hall').errorReminder('连接服务器失败!');
            // getGI('offlineAlert').showOfflineAlert('show'); //显示离线提示弹出框
        }).close(function () {
            //关闭连接
            //关闭连接500毫秒后，检测
            if (room) {
                // clientUser.sleep(); //房间进入等待状态(自己不能再出牌)
            }
        });
    },

    /**
     * 转换操作消息中的t字段
     * operateArr =  [
        {p: '289383',  t: 'pong', c: 'w3', from: 3892938, m:['w3', 'w3'] }, //碰
        {p: '289383',  t: 'mingGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3'] }, //明杠
        {p: '289383',  t: 'anGang', c: 'w3', from: 3892938, m:['w3', 'w3', 'w3', 'w3'] }, //暗杠
        {p: '289383',  t: 'buGang', c: 'w3', from: 3892938, m:['w3'] }, //补杠, 与碰是分开的
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w3', 'w4'] }, //吃
        {p: '289383',  t: 'chi', c: 'w2', from: 3892938, m:['w1', 'w3'] }, //吃
     *  ]
     * @public
     * @param {Array|obj}
     */
    converOperate: function converOperate(operate) {
        if (!operate) {
            return;
        }
        if (operate.length === undefined) {
            operate.t = window.messageType[operate.t];
        } else {
            for (var i = 0; i < operate.length; i++) {
                operate[i].t = window.messageType[operate[i].t];
            }
        }

        return operate;
    },

    /**
     * 回到大厅界面
     */
    backHall: function backHall() {
        window.reset();
        //显示用户的信息
        getGI('hall').display({
            nickName: clientUser.attr('nickName'), //昵称
            userId: clientUser.attr('userId'), //用户Id
            headIcon: clientUser.attr('faceIcon'), //头像图标
            roomCardNum: clientUser.attr('roomCards'), //房卡数
            isVip: clientUser.attr('isVip') });
    },


    /**
     * 删除掉操作中的  开始单游 开始双游 开始三游
     */
    deleteStart: function deleteStart(user, optArr) {
        if (optArr && optArr.length > 0) {
            for (var i = 0; i < optArr.length; i++) {
                var type = void 0;
                switch (optArr[i].t) {
                    case 23: //开始单游
                    case 24: //开始双游
                    case 25:
                        //开始三游
                        var optObj = optArr.splice(i, 1)[0];
                        user.attr('youJingCards', optObj.m);
                        user.attr('youJingCards').type = window.messageType[optObj.t]; //记录类型
                        console.log(user.attr('youJingCards'));

                        break;
                }
            }
        } else {
            user.attr('youJingCards', null);
        }
    }
};

module.exports = TOOL;

cc._RFpop();
},{"controllerMapMng":"controllerMapMng","msgCache":"msgCache","socketLocalMng":"socketLocalMng","socketMng":"socketMng"}],"userBase":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd7df1ntA/JALYuP2zBOvFXb', 'userBase');
// scripts\common\controller\userBase.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var Combination = require('combination'); //状态类
var MahjongMng = require('mahjongMng'); //状态类

/**
 * @class
 * @classdesc -- 管理单个用户信息，用户的泛化类
 */
var UserBase = cc.Class({
    name: 'UserBase',
    extends: Combination, //注意，这里继承了 组合类
    /**
     * @constructor
     * @param {Object} config --
     *  {
     *    //可以省略，初始化用户数据
     *    attr: {cards: 1000,  faceIcon:"5350b36d56b6e054.jpg",  nickName:"玩家10003", openId:"10004", userId:10004}
     *
     *  }
     */
    ctor: function ctor() {
        var config = arguments[0];

        var attr = config ? config.attr : undefined;

        this.combination([//用户数据的存储
        { name: 'userInfo', combineType: 'data', type: 'class', config: attr }, //基本信息
        { name: 'userState', combineType: 'data', type: 'class', config: attr }]);

        //牌的操作对象
        this.mahjongMng = null;
    },

    reset: function reset() {
        console.log('执行了userBase.reset方法');
        this.reset_other(); //组合中的其它reset方法
        this.mahjongMng && this.mahjongMng.reset();
    },

    /**
     * 解散后或八局结束后，进行的重置，针对客户
     * @public
     */
    resetJu: function resetJu() {
        //牌的操作对象
        this.mahjongMng = null;

        this.remove(['userResource', 'userHeadShow', 'userMsgShow']);
    },

    /**
     * 如果有人离线，自己就不能有操作了
     * @public
     */
    sleep: function sleep() {
        if (this.mahjongMng) {
            //将自己能否出牌存起来
            this.attr('isCanPlay', this.mahjongMng.mahView.outCardEventAttr('isCanPlay'));

            this.mahjongMng.mahView.outCardEventAttr('isCanPlay', false); //禁用用出牌
        }

        this.attr('isCanOperateClick', false); //禁用选项面板上的点击事件
        console.log('sleep...');
    },

    setAttr: function setAttr(name, value) {
        switch (name) {
            case 'isCanPlay':
                if (value != undefined) {
                    this.attr('isCanPlay', value);
                    return this.mahjongMng.mahView.outCardEventAttr('isCanPlay', value); //设置能否出牌
                } else {
                    return this.mahjongMng.mahView.outCardEventAttr('isCanPlay'); //返回能否出牌
                }
            case 'isCanOperateClick':
                if (value != undefined) {
                    return this.attr('isCanOperateClick', value); //设置能否出牌
                } else {
                    return this.attr('isCanOperateClick'); //返回能否出牌
                }
            case 'isCanOperate':
                if (value != undefined) {
                    return this.mahjongMng.mahView.outCardEventAttr('isCanOperate', value); //设置能否出牌
                } else {
                    return this.mahjongMng.mahView.outCardEventAttr('isCanOperate'); //返回能否出牌
                }
        }
    },

    /**
     * 当所有人都在线时，可以进行正常的操作
     * @public
     */
    wake: function wake() {
        if (this.mahjongMng) {
            this.mahjongMng.mahView.outCardEventAttr('isCanPlay', this.attr('isCanPlay')); //设置出牌状态
        }
        this.attr('isCanOperateClick', true); //启用用选项面板上的点击事件
        console.log('wake...');
    },

    /**
     * 拿牌
     * @public
     * @param {string} cardName -- 要拿的牌 可以是 'w3' 或 ''
     * @param {string} isCalcHu -- 传入 false或不传入表示不计算，传入''表示直接计算(不加减牌), 传入'w2'表示减去这张牌后计算后再加上
    */
    inCard: function inCard(cardName, restNum, optArr) {
        // audio.play('tray');
        console.log('执行了userBase.inCard' + cardName + ',' + restNum);
        this.mahjongMng.inCard(cardName, optArr);

        //显示剩余的牌数
        room.displayRoomInfo('restCardNum', restNum);
    },

    /**
     * 出牌
     * @public
     * @param {string} cardName -- 要出的牌
     * @param {string} optArr -- 能进行的操作
    */
    outCard: function outCard(cardName) {
        var self = this;
        this.mahjongMng.outCard(cardName, function () {
            //    audio.play(self.attr('whoseVoice'), cardName); //播放点击时的声音
        });

        if (this.attr('isClient')) {
            //如果是客户

            //将自己的选择清空
            clientUser.attr('chooseIten', null);
        }

        window.audio.playMj(this.attr('whoseVoice') + '_' + cardName);

        if (!this.attr('firstOutCard')) {
            //如果是自己出的第一张牌, 就记录下来
            this.attr('firstOutCard', cardName); //记录自己出的第一张牌
        }
    },

    /**
     * 弹出选项面板
     * @param {Array} optArr -- 操作的数据
     *  optArr =  [
     *               {operateName:'guo', from: '3892'},
     *               {operateName:'anGang', operateCard: 'w3', from: '3892'}, //可能同时有多个暗杠
     *               {operateName:'mingGang', operateCard: 'w5', from: '3892'},
     *               {operateName:'buGang', operateCard: card, index: i, from: '3892'} //可能同时有多个补杠
     *           ]
     */
    showPanel: function showPanel(optArr) {
        this.mahjongMng.showPanel(optArr);
    },
    /**
     * 添加并显示一副牌
     * @public
     * @param {Object} config -- {handCards: 17 |  [ 'e', 'w2', 't8', 'n'] , outCards: null, mingCards: null, huaCards: users[i].hua,}
     * 不可省略， 玩家最开始时拿到的牌
     *
    */
    setCards: function setCards(config) {
        this.mahjongMng.setCards.apply(this.mahjongMng, arguments);
        this.attr('originalCards', config.handCards); //存原始牌
        this.attr('haveCard', _typeof(config.handCards) === 'object'); //标记自己是否有牌
    },

    showCards: function showCards() {
        this.mahjongMng.showCards.apply(this.mahjongMng, arguments);
    },

    /**
     * 开始补花
     */
    startBuHua: function startBuHua() {
        this.mahjongMng.startBuHua.apply(this.mahjongMng, arguments);
    },

    combineSend: function combineSend(bool) {
        if (this.attr('isHasCombined') || bool) {
            //如果已经结合过了
            return;
        }
        //发送消息的管理
        this.sendMessageMng = socket.setID(this.attr('userId'));

        /**
         * 发送的消息格式: obj = {type:'', openId:'', message: ''}
         * 传入的消息格式：obj = {type:'', message: ''} //这个是默认值
         * 对消息格式进行转换，转换后：{t: obj.type | '', p: obj.openId | meOpenId, m: obj.message | ''}
         * @public
         * @param {object} obj -- 必须是一个对象
         * @returns {this}
         */
        this.send = function (dataObj) {
            this.sendMessageMng.send(dataObj); //发送消息
            return this;
        };
    },

    /**
     * 组合其它类的方法和数据
     * @public
     */
    combineAction: function combineAction() {
        console.log('执行了 userBase - combineAction。。');
        console.log('结合了combineAction');
        this.combination([//用户数据的存储
        { name: 'userResource', combineType: 'data', type: 'class', config: { userSelf: this } }, //本用户引用的所有资源

        //显示用户头像
        { name: 'userHeadShow', combineType: 'method', type: 'class', config: { userSelf: this } }]);

        //显示用户发的表情文字
        this.combination([{ name: 'userMsgShow', combineType: 'method', type: 'class', config: this.attr('userHeadNode') }]);
    },

    /**
     * 组合操作麻将相关的方法
     * @public
     */
    combineMahjong: function combineMahjong(operateArr) {
        console.log('执行了 userBase - combineMahjong。。');
        this.attr('isHasCombined', true);
        //牌的操作
        this.mahjongMng = new MahjongMng({
            userSelf: this });

        operateArr && this.mahjongMng.displayAllMing(operateArr);
    }

});

module.exports = UserBase;

cc._RFpop();
},{"combination":"combination","mahjongMng":"mahjongMng"}],"userHeadShow":[function(require,module,exports){
"use strict";
cc._RFpush(module, '24f412GFEdPGrklf5Cza0uu', 'userHeadShow');
// scripts\room\view\userLayer\display\userHeadShow.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * @class
 * @classdesc -- 这个类用于显示用户头像及其周围的信息  使用方式： user.displayHead('youJin', 'sanYouZhong');
 */
var UserHeadShow = cc.Class({
    name: 'UserHeadShow',

    /**
     * @constructor
     * @param {Object} config -- {
     *                              userSelf //用户自己
     *                           }
     */
    ctor: function ctor() {
        var config = arguments[0];

        this.userNode = config.userSelf.attr('userHeadNode'); //用户自己; //用户头像节点
        this.mainGame_Atlas = getGI('roomGlobalRes').mainGame_Atlas; //头像的结点
        this.operate_Atlas = getGI('roomGlobalRes').operate_Atlas; //操作的图集
    },

    /**当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        console.log('userHeadShow.js重置了。');
        this.userNode.getChildByName('youJing').active = false; //隐藏游金中
        this.userNode.getChildByName('readyStr').active = false; //隐藏OK
    },

    /**
    * 显示、隐藏、设置头像周围的数据
    * 示例： displayHead('score', 20); //显示并更新分数
    *        displayHead({score: 30, nickName: '我是一个玩家', 'ting': 'hide'}); //更改分数、玩家 ,隐藏听
    * @param {string|Object} 当传入的是string时，attrName_obj  ==》  在页面中显示或隐藏
    *                       attrName_obj可取：'zhuang'，'nickName'，'faceIcon'，'ting'，'score'，'ok'，'leaveLine'，'message'
    * @param {string} value -- 当第一个参数是对象时，可省略，
    *                          'show'或'hide'表示显示或隐藏，其它值表示设置属性
    *                          score、nickName中的其它值(非show/hide)，表示更改并显示分数、昵称
    */
    displayHead: function displayHead(attrName_obj, value) {
        if ((typeof attrName_obj === 'undefined' ? 'undefined' : _typeof(attrName_obj)) === 'object') {
            for (var prop in attrName_obj) {
                this._setOneAttrDisplay_(prop, attrName_obj[prop]);
            }
        } else {
            this._setOneAttrDisplay_(attrName_obj, value);
        }
    },

    /**
     * 显示、隐藏、设置一项数据
     * @private
     * @param {string} attrName -- attrName  ==》  在页面中显示或隐藏
     *
     * @param {string} value -- 不可省略，'show'或'hide'表示显示或隐藏，其它值表示设置属性
     *                          score  nickName中的其它值(非show/hide)，表示更改并显示分数、昵称
     */
    _setOneAttrDisplay_: function _setOneAttrDisplay_(attrName, value) {
        switch (attrName) {
            case 'zhuang':
                //设置是否显示庄
                cc.find('zhuanIcon', this.userNode).active = value === 'show' ? true : false;
                break;
            case 'faceIcon':
                //显示指定的头像
                TOOL.setUserHeadIcon(this.userNode.getChildByName('userHeadIcon'), value);
                break;
            case 'nickName':
                var scoresNode = this.userNode.getChildByName('nickBorder'); //昵称节点
                this._setNick_(scoresNode, 'nickName', value);
                break;
            case 'isHost':
                //设置是否显示房主
                cc.find('cap', this.userNode).active = value === 'show' ? true : false;
                break;
            case 'score':
                //控制分数
                var scoresNode = this.userNode.getChildByName('nickBorder'); //昵称节点
                this._setNick_(scoresNode, 'score', value);
                break;
            case 'ok':
                //设置是否显示OK
                this.userNode.getChildByName('readyStr').active = value === 'show' ? true : false;
                break;
            //youJingZhong shuangYouZhong  sanYouZhong
            case 'youJing':
                //设置显示 在单游中 在双游中 在三游中 或 隐藏和显示
                var node = this.userNode.getChildByName('youJing');
                if (value === 'show') {
                    node.active = true;
                } else if (value === 'hide') {
                    node.active = false;
                } else {
                    node.active = true;
                    node.getComponent(cc.Sprite).spriteFrame = this.operate_Atlas.getSpriteFrame(value);
                }

                break;
            case 'leaveLine':
                //设置是否显示离线
                cc.find('leaveLine', this.userNode).active = value === 'show' ? true : false;
                break;
        }
    },

    /**
     * 设置并显一个节点，或者设置节点的Label属性
     * @private
     * @param {cc.Node} node -- 要设置的节点
     * @param {number|string} value -- 'show'表示显示，'hide'表示隐藏，其它的值表示显示和更改
     */
    _setNick_: function _setNick_(node, str, value) {
        if (value === 'show') {
            //显示
            node.active = true; //显示节点
        } else if (value === 'hide') {
            node.active = false; //隐藏节点
        } else {
            //更改、显示节点
            node.active = true; //显示节点
            node.getChildByName(str).getComponent(cc.Label).string = (value > 0 ? '+' : '') + value; //设置节点的值
        }
    }

});

module.exports = UserHeadShow;

cc._RFpop();
},{}],"userInfoAlert":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8b60aufZVBCT5nAfF7z1/80', 'userInfoAlert');
// scripts\room\view\alert\userInfoAlert.js

'use strict';

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * @class
 * @classdesc -- 用户信息弹出框
 */
cc.Class({
    extends: cc.Component,

    properties: {
        atlas: cc.SpriteAtlas },

    onLoad: function onLoad() {
        this.headNode = null; //头像的节点
    },

    /**
     * 显示或隐藏弹出框
     * 1.挂载于 hall场景中的 用户头像上的点击事件
     * 2.挂载于 room场景中的 四个用户头像上的点击事件
     * @public
     * @param {string} isShow -- 'show'表示显示, 'hide'表示隐藏
     */
    node_showUserInfoAlertClick: function node_showUserInfoAlertClick(event, isShow) {
        window.audio.playEffect('waterClick'); //播放点击声音
        if (isShow === 'show') {
            var user = room.getUser(parseInt(event.target.parent.name.slice(-1)));

            if (user) {
                var obj = user.attr({ nickName: undefined, userId: undefined, ip: undefined, roomCards: undefined, faceIcon: undefined });
            } else {
                return;
            }
            this.headNode = cc.find('alertContent/userInfoAlertBg/noHeade', this.node); //头像的节点
            this.setserInfoAlertData(obj); //更新数据
        }

        this.isShow = isShow === 'show' ? true : false;
        this.node.active = this.isShow;
    },

    /**
     * 显示用户相关信息
     * @public
     * @param {object|string} key -- 必须，如果是对象，则忽略第二个参数。如果是字符串，就读取第二个参数
     *                               如：{nickName：'玩家12333', userId: 38929, ip: '192.168.0.104', faceIcon: 'man1' }
     * @param {string} value -- 可省略，要设置的值
     */
    setserInfoAlertData: function setserInfoAlertData(key, value) {
        if ((typeof key === 'undefined' ? 'undefined' : _typeof(key)) === 'object') {
            var keyValueObj = key;
            for (var prop in keyValueObj) {
                this._showOne_(prop, keyValueObj[prop]);
            }
        } else {
            this._showOne_(key, value);
        }
    },

    /**
     * 显示用户相关信息
     * @private
     * @param {string} key -- 必须, 'nickName' 或 'userId' 或 'ip'
     * @param {string} value -- 必须，要设置的值
     */
    _showOne_: function _showOne_(key, value) {
        var nodeName;
        switch (key) {
            case 'faceIcon':
                //头像图片
                TOOL.setUserHeadIcon(cc.find('alertContent/userInfoAlertBg/headIcon', this.node), value);
                return;
            case 'nickName':
                //昵称
                cc.find('alertContent/userInfoAlertBg/nickName', this.node).getComponent(cc.Label).string = value;
                break;
            case 'roomCards':
                cc.find('alertContent/userInfoAlertBg/diamond', this.node).getComponent(cc.Label).string = '钻石: ' + value + '颗';
                break;
            case 'userId':
                //用户id
                cc.find('alertContent/userInfoAlertBg/ID', this.node).getComponent(cc.Label).string = 'ID:' + value;
                break;
            case 'ip':
                //ip地址
                cc.find('alertContent/userInfoAlertBg/IP', this.node).getComponent(cc.Label).string = 'IP:' + value;
                break;
        }
    }

});

cc._RFpop();
},{}],"userInfo":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7e4d6/RI+pFALcCTKAD0LfB', 'userInfo');
// scripts\room\model\user\userInfo.js

'use strict';

/**
 * @class
 * @classdesc -- 管理单个用户信息
 */
var UserInfo = cc.Class({
    name: 'UserInfo',

    /**
     * @constructor
     * @param {object|false} config -- 如果是false, 就什么也不做， 否则，如果UserInfo中已经定义有某个属性，就赋值
     *                                 如：{faceIcon： '3333.jpg', roomCards: 5000} //这些属性必须是UserInfo中已经定义的，否则不会有任何作用
     */
    ctor: function ctor() {
        var config = arguments[0];

        this.faceIcon = ''; //头像
        this.nickName = ''; //用户的昵称
        this.openId = ''; //用户的openId
        this.userId = 0; //用户ID
        this.roomCards = 0; //房卡数
        this.ip = ''; //ip地址
        this.isVip = false; //是不是vip
        this.isHost = false; //是不是房主

        this.joinOrder = 0; //自己是第几个加入房间 取值 [0,3]。0表示创建房间，1表示第一个加入房间，2表示第二个加入房间，3表示第三个加入房间
        this.positionOrder = 0; //自己在房间中的位置号 取值 [0,3]。0表示自己这方，1表示左边， 2表示对面，3表示右边
        this.sex = 'man'; //是男的还是女的, 'man' 或 'woman'
        this.whoseVoice = 'g'; //是哪个人的声音 可取 g 或 b
        this.state = 0; //0-离线 1-在线

        this.haveCard = false; //自己是否有牌，有可能四个人都有牌
        this.isClient = false; //是不是使用手机的那个用户(人)
        this.isActive = false; //当前用户是否是活动用户
        this.isHasCombined = false; //是否结合过一次了

        if (!config) {
            return;
        }
        for (var prop in config) {
            //config不会创建新的属性
            if (this[prop] !== undefined) {
                this[prop] = config[prop];
            }
        }
    },

    reset: function reset() {
        console.log('userInfo.js重置了。。');
        this.isActive = false; //当前用户是否是活动用户
    }
});

module.exports = UserInfo;

cc._RFpop();
},{}],"userMsgShow":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f0ca9gTDWZIdKAswuFMEQYY', 'userMsgShow');
// scripts\room\view\userLayer\display\userMsgShow.js

'use strict';

/**
 * @class
 * @classdesc -- 这个类用于显示用户发的消息
 */
var UserMsgShow = cc.Class({
    name: 'UserMsgShow',

    /**
     * @constructor
     * @param {Object} config -- {
     *                              userSelf //用户自己
     *                           }
     */
    ctor: function ctor() {
        var config = arguments[0];

        //    this.userNode = config.userSelf.attr('userHeadNode'); //用户头像节点
        this.userNode = config;
        this.roomComponent = getGI('roomGlobalRes'); //用于挂载定时器
        this.face_Atlas = this.roomComponent.face_Atlas; //表情的图集

        this.msgShowTime = 3; //消息框显示3秒，然后自己消失
        this._finishedBind_ = null; //消息完成后的回调(将this邦定为UserMsgShow的对象)
    },

    //测试用，更改用户节点
    test_setUser: function test_setUser(userNode) {
        this.userNode = userNode;
    },

    /**
     * 隐藏或显示或设置别人发的信息(包括文字和表情)
     * @private
     * @param {string|object} value -- 'show'表示显示，'hide'表示隐藏，如果是一个对象，则表示显示和更改
     *                                     {type: 'face', value: 'FE12'} {type: 'word', value: 'c02', whoseVoice: 'g'}
     */
    say: function say(value) {
        if (value === 'show') {
            //显示消息框
            this._setMsgState_('show');
        } else if (value === 'hide') {
            //隐藏消息框
            this._setMsgState_('hide');
        } else {
            //更改、显示消息
            if (value.type === 'face') {
                this._showFace_(value.value);
            } else if (value.type === 'word') {
                this._showMsg_(value.value, value.whoseVoice);
            }
        }
    },

    /**
     * 隐藏或显示别人发的信息(包括文字和表情)
     * @private
     * @param {boolean} isShow -- true表示显示消息框，false表示隐藏消息框
     */
    _setMsgState_: function _setMsgState_(alert, isShow) {
        var faceAlert = false,
            msgAlert = false;
        if (alert === 'faceAlert') {
            faceAlert = isShow;msgAlert = !isShow;
        } else if (alert === 'msgAlert') {
            msgAlert = isShow;faceAlert = !isShow;
        } else if (alert === 'show') {
            faceAlert = msgAlert = true;
        }

        this.userNode.getChildByName('faceAlert').active = faceAlert;
        this.userNode.getChildByName('msgAlert').active = msgAlert;
    },

    /**
     * 显示别人发的表情
     * @private
     * @param {cc.SpriteFrame} faceName -- 别人发的表情对应的图片的名字 ，如： 'EE'
     */
    _showFace_: function _showFace_(faceName) {

        this.roomComponent.unschedule(this._finishedBind_); //取消原定时器
        this._finishedBind_ = this._finished_.bind(this);
        this.roomComponent.scheduleOnce(this._finishedBind_, this.msgShowTime);

        this._setMsgState_('faceAlert', true); //显示消息框
        var faceNode = cc.find('faceAlert/FE', this.userNode); //表情节点
        faceNode.getComponent(cc.Sprite).spriteFrame = this.face_Atlas.getSpriteFrame(faceName); //更改表情
    },

    /**
     * 消息显示完成后的回调
     */
    _finished_: function _finished_() {
        this._setMsgState_('hide'); //隐藏信息
    },

    /**
     * 显示别人发的文字
     * @private
     * @param {string} wordNum -- 别人发的文字的编号 'c05'
     */
    _showMsg_: function _showMsg_(wordNum, whoseVoice) {
        this.roomComponent.unschedule(this._finishedBind_); //取消原定时器
        this._finishedBind_ = this._finished_.bind(this);
        this.roomComponent.scheduleOnce(this._finishedBind_, this.msgShowTime);

        this._setMsgState_('msgAlert', true); //显示消息框
        var wordNode = cc.find('msgAlert/word', this.userNode); //文本节点
        wordNode.getComponent(cc.Label).string = window.words.data[wordNum]; //更改文字  'c05'
        window.audio.chat(whoseVoice + '_' + wordNum); //'g_c02'
    }
});

module.exports = UserMsgShow;

cc._RFpop();
},{}],"userResource":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8fb7fyqtPlJYak9O0nvKVqK', 'userResource');
// scripts\room\model\user\userResource.js

'use strict';

/**
 * @class
 * @classdesc -- 管理单个用户信息
 */
var UserResource = cc.Class({
         name: 'UserResource',

         /**
          * @constructor
          * @param {object|false} config -- 如果是false, 就什么也不做， 否则，如果UserResource中已经定义有某个属性，就赋值
          *                                 如：{userSelf: this} //这些属性必须是UserResource中已经定义的，否则不会有任何作用
          */
         ctor: function ctor() {
                  var config = arguments[0];

                  var index = config.userSelf.attr('positionOrder'); //本用户的位置顺序
                  var roomGlobalRes = getGI('roomGlobalRes');

                  this.playingLayer = roomGlobalRes.playingLayer; //牌的显示层节点

                  this.userNode = this.playingLayer.getChildByName('user' + index); //用户节点
                  this.showNode = this.userNode.getChildByName('show'); //碰、扛的牌的父结点
                  this.handNode = this.userNode.getChildByName('hand'); //手中牌的父节点
                  this.userHeadNode = cc.find('Canvas/roomMain/user' + index); //用户头像节点

                  if (!config) {
                           return;
                  }
                  for (var prop in config) {
                           //config不会创建新的属性
                           if (this[prop] !== undefined) {
                                    this[prop] = config[prop];
                           }
                  }
         }
});

module.exports = UserResource;

cc._RFpop();
},{}],"userStateBase":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'edfb99wD4ZMo5Us+a7Y9lok', 'userStateBase');
// scripts\room\model\user\userStateBase.js

'use strict';

/**
 * @class
 * @classdesc -- 管理单个用户信息
 */
var UserStateBase = cc.Class({
    name: 'UserStateBase',

    /**
     * @constructor
     * @param {object|false} config -- 如果是false, 就什么也不做， 否则，如果UserInfo中已经定义有某个属性，就赋值
     *                                 如：{faceIcon： '3333.jpg', roomCards: 5000} //这些属性必须是UserInfo中已经定义的，否则不会有任何作用
     */
    ctor: function ctor() {
        var config = arguments[0];

        this.zhuang = false; //是否是庄家
        this.ting = false; //是否听了

        this.originalCards = null; //拿到的牌， 如果是自己就是一个数组['w2', 't8',...] 如果是别人 就是一个数字 14
        this.currentScore = 0; //当前分数
        this.roomScore = 0; //总分数

        this.isReady = false; //自己是不是准备好了, 发牌后置为false

        if (!config) {
            return;
        }
        for (var prop in config) {
            //config不会创建新的属性
            if (this[prop] !== undefined) {
                this[prop] = config[prop];
            }
        }
    },

    /**
     * 当新一局开始时重置本对象
     * @public
    */
    reset: function reset() {
        console.log('userStateBase.js重置了。。');
        this.zhuang = false; //是否是庄家
        this.ting = false; //听
        this.originalCards = null; //拿到的牌， 如果是自己就是一个数组['w2', 't8',...] 如果是别人 就是一个数字 14
        this.isReady = false; //自己是不是准备好了, 发牌后置为false
    }

});

module.exports = UserStateBase;

cc._RFpop();
},{}],"userState":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f74f3LBPN9CnJ2/r7iMp6LG', 'userState');
// scripts\room\model\user\userState.js

'use strict';

var UserStateBase = require('userStateBase');

/**
 * @class
 * @classdesc -- 管理单个用户信息
 */
var UserState = cc.Class({
         name: 'UserState',
         extends: UserStateBase,

         /**
          * @constructor
          * @param {object|false} config -- 如果是false, 就什么也不做， 否则，如果UserInfo中已经定义有某个属性，就赋值
          *                                 如：{faceIcon： '3333.jpg', roomCards: 5000} //这些属性必须是UserInfo中已经定义的，否则不会有任何作用
          */
         ctor: function ctor() {
                  var config = arguments[0];

                  this.cardsHandOrder = {}; //时该记录每个节点对应于哪一张牌，以后出牌时，选中节点后才知道是哪张牌  注意，只有自己才会有这个

                  this.firstOutCard = ''; //自己出的第一张牌，如果自己没出过牌，则这个值是一个空字符串
                  this.finishCheckClick = true; //对于别人出的牌，自己是不是已经处理完成了，true表示完成

                  /*
                  * 记录胡牌的信息, 可能有多次胡
                  * huInfoArr = [{huCard: 'w2', cardFrom: 'openId', huType: 'jiePao', why: 'mingGang', huDetail: {qiangGang: openId} }, ]
                  * huType = 'jiePao' | 'zimo'
                  * why = outCardReason | naPaiReason
                  * huDetail 用于记录胡牌的其它信息， 如 抢扛，如果没有就设置为空字符串
                  * 当自己没有胡牌时，是一个空数组
                  */
                  this.huInfoArr = [];

                  this.naPaiInfo = { cardName: '' };

                  this.youJingCards = null; //记录想要游金必须出的牌 ['w2', 'w3'].type = 'startYouJing' || startShuangYou || startSanYou

                  //[ {h: 'i2', c: 'i3'}, {h: 'i3', c: 'w2'} ]
                  this.huaCards = null; //记录要补的花
                  this.processPeriod = 'init'; // init - 初始状态，inCard - 拿了一张牌  waiting --

                  /*
                   * 计算的操作(弹出选项面板后，点击时)
                   * operateArr =  [
                   *     {operateName:'guo', from: '3892'},
                   *     {operateName:'anGang', operateCard: 'w3', from: '3892'}, //可能同时有多个暗扛
                   *     {operateName:'mingGang', operateCard: 'w5', from: '3892'},
                   *     {operateName:'buGang', operateCard: card, index: i, from: '3892'} //可能同时有多个补扛
                   *  ]
                   */
                  this.operateArr = null;

                  //自己在面板中选的是哪一项, 只要自己出了一项后，就清空
                  this.chooseIten = null;

                  this.isCanPlay = false; //是否可以出牌，用于重连判断
                  this.isCanOperateClick = true; //选项面板上的选项是否可以被点击选择，用于重连判断

                  if (!config) {
                           return;
                  }
                  for (var prop in config) {
                           //config不会创建新的属性
                           if (this[prop] !== undefined) {
                                    this[prop] = config[prop];
                           }
                  }
         },

         /**
          * 当新一局开始时重置本对象
          * @public
         */
         reset: function reset() {
                  console.log('userState.js重置了。。');
                  this._super();
                  this.firstOutCard = ''; //自己出的第一张牌，如果自己没出过牌，则这个值是一个空字符串
                  this.finishCheckClick = true; //对于别人出的牌，自己是不是已经处理完成了，true表示完成

                  this.huInfoArr.length = 0; //记录胡牌的信息

                  this.operateArr = null; //计算的操作(弹出选项面板后，点击时)
                  this.chooseIten = null; //自己在面板中选的是哪一项, 只要自己出了一项后，就清空

                  this.isCanPlay = false; //是否可以出牌，用于重连判断
                  this.isCanOperateClick = true; //选项面板上的选项是否可以被点击选择，用于重连判断

                  this.naPaiInfo = { cardName: '' };
         }
});

module.exports = UserState;

cc._RFpop();
},{"userStateBase":"userStateBase"}],"user":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0dbeag/FOpClK1qaAd40OYK', 'user');
// scripts\common\controller\user.js

'use strict';

var UserBase = require('userBase'); //用户基类
/**
 * @class
 * @classdesc -- 管理单个用户信息
 */
var User = cc.Class({
    name: 'User',
    extends: UserBase,
    /**
     * @constructor
     */
    ctor: function ctor() {},

    /**
     * 一局结束时调用
     * @public
     * @param {object} data -- 如果没牌了，传入null或不传入，否则传入 { type: 'hu', message: '{huCard: 'w2',from: '384827'}', openId: '10114'}
     */
    juEnd: function juEnd(data) {
        console.log('发送了一局结束的消息');
        room.displayPointer(-1); //停止中间的计时器，并且不指向任何一个人

        this.send({ type: 'juScore' });
    },

    /**
     * 选择某一操作后进行的处理（如，点了碰后）
     * hu, pong, guo, chi, ting, mingGang, anGang, buGang
     * @public
     * @param {Object} operateData -- 操作的数据
     * 示例：operateData = {p: who, t: 'buHua', c: 'i1', from: who, m:[]}
     */
    operateItem: function operateItem(operateData) {
        this._choose_(operateData); //进行处理
        this.mahjongMng.operateItem(operateData); //显示和改数据
    },

    /**
     *
     * @private
     */
    _choose_: function _choose_(operateData) {
        var operateName = operateData.t; //hu, pong, guo, chi, ting, mingGang, anGang, buGang, buHua
        switch (operateName) {
            case 'buHua':
                // this._buHua_(operateData);
                // window.audio.playMj( this.attr('whoseVoice') + '_buHua' );
                break;
            case 'pong':
                window.audio.playMj(this.attr('whoseVoice') + '_pong');
                room.getUser(operateData.from).backOutCard();
                break;
            case 'chi':
                window.audio.playMj(this.attr('whoseVoice') + '_chi');
                room.getUser(operateData.from).backOutCard();
                break;
            case 'mingGang':
                room.getUser(operateData.from).backOutCard();
                window.audio.playMj(this.attr('whoseVoice') + '_gang');
                break;
            case 'anGang':
                if (this.attr('haveCard')) {
                    //如果有牌
                    this.send({ type: operateData.t, message: TOOL.converOperate(operateData) }); //发送暗杠的消息
                    TOOL.converOperate(operateData);
                }
                window.audio.playMj(this.attr('whoseVoice') + '_gang');
                break;

            case 'youJing':
            case 'shuangYou':
            case 'sanYou':
            case 'sanJingDao':
                window.audio.playMj(this.attr('whoseVoice') + '_hu');
                console.log('user.js- ' + operateName);
                break;

            case 'buGang':
                window.audio.playMj(this.attr('whoseVoice') + '_gang');
                break;
            case 'hu':
                window.audio.playMj(this.attr('whoseVoice') + '_hu');
                break;
        }
    },

    /**
     * 操作出的牌的后退
     * @public
     */
    backOutCard: function backOutCard() {
        //回退一张
        var result = this.mahjongMng.mahView.operateOutCard('backward');
        //如果是由于抢的补杠，而碰的，就不能回退
        if (result === 'chiangBuGang') {
            return;
        }
        result.active = false;
        //停止小红点的动画
        room.finishOutSkipAni();
    }

});

module.exports = User;

cc._RFpop();
},{"userBase":"userBase"}]},{},["audioMng","combination","mahjongMng","mahjongMngBase","buGangCheck_controller","dismiss_controller","error_controller","isOnline_controller","join_controller","juEnd_controller","msgSend_controller","napai_controller","operate_controller","outCard_controller","rebuild_controller","rebuild_msg_controller","record_controller","share_controller","start_controller","msgComm","msgStartRun","user","userBase","dialogWords","global","globalData","helpText","conditions","controllerMapMng","msgCache","msgConfig","socketLocalMng","socketMng","pageLog","test_allResultData","test_cardsData","test_createRoom","test_createUser","test_data","test_singleResultData","tools","_node_hall_click","inputNumPanel","node_hall_broadcast","node_hall_display","node_helpAlert","node_recordAlert","node_settingAlert","_node_login","handCards","handCardsBase","operateCards","outCards","calcHuMng","cardsHu_other","cardsHu_pair","cardsMng","cardsAll","handCardsChild","operateCards_operate","outCards_operate","room","roomData","userInfo","userResource","userState","userStateBase","allResultAlert_click","allResultAlert_display","dismissAlert_click","dismissAlert_display","messageAlert_click","messageAlert_display","singleResult_click","singleResult_display","userInfoAlert","_node_operatePanelEvent","hand_outCardEvent","_node_room_click","animate_countDown","animate_head","animate_skip","roomDisplay","roomGlobalRes","animate_outCard","handCardMng","hand_operatePanel","mingCardMng","outCardMng","userHeadShow","userMsgShow","msgs","msgsMng","rebuildMsgMng","serverControllerMapMng","calc","isOnline_serverController","join_serverController","operate_serverController","record_serverController","start_serverController","serverGlobal","serverMsgConfig","serverMsgData"])

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZXNDb250ZW50IjpbXSwic291cmNlUm9vdCI6IiJ9