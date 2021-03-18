function categoryIndex(cagegoryId) {
    window.location.href = "/customer/index?categoryId=" + cagegoryId;
}

function articleDetail(id) {
    window.location.href = "/customer/detail/article?id=" + id;
}

function focusArticle() {
    window.location.href = "/customer/index?focusArticle=" + 1;
}

function admireArticle() {
    window.location.href = "/customer/index?admireArticle=" + 1;
}

function commentArticle() {
    window.location.href = "/customer/index?commentArticle=" + 1;
}

function detailUser(id) {
    window.location.href = "/customer/detail/personal?id=" + id;
}

function detailNotice(id) {
    window.location.href = "/customer/detail/notice/main?id=" + id;
}

function noticeIndex() {
    window.location.href = "/customer/detail/notice/index";
}

function detailFocus(id) {
    window.location.href = "/customer/detail/focus?id=" + id + "&&action=focus"
}

function detailFans(id) {
    window.location.href = "/customer/detail/focus?id=" + id + "&&action=fans"
}

var layer;
layui.use('layer', function () {
    layer = layui.layer;
})
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function changeIcon(id) {
    layer.open({
        type: 2,
        area: ['550px', '550px'],
        fixed: false, //不固定
        maxmin: true,
        shadeClose: true,
        skin: 'open',
        title: '头像设置',
        content: ['/changeIcon?id=' + id, 'no'],
        success: function (layero, index) {
            console.log("success");
        },
        end: function () {

        }
    });
}

function inform(id) {
    window.location.href = "/customer/detail/inform?id=" + id;
}

function chat(id) {
    window.location.href = "/customer/chat/index?id=" + id;
}

function focusSb(id, obj) {
    if ($(obj).attr("class") == "focus-y") {
        $.ajax({
            cache: false,
            async: false,
            type: 'post',
            url: '/customer/detail/focus/focusBody',
            data: {
                'id': id,
                'type': 'Y'
            },
            success: function (data) {
                if (data == true) {
                    layer.msg("取消关注成功");
                    $(obj).html("<i class=\"fa fa-plus\" style=\"margin-right: 10px\"></i>关注");
                    $(obj).attr("class", "focus-n");
                }
                else {
                    layer.msg("取消关注失败，请重试", {icon: 2});
                }
            }
        })

    }
    else {
        $.ajax({
            cache: false,
            async: false,
            type: 'post',
            url: '/customer/detail/focus/focusBody',
            data: {
                'id': id,
                'type': 'N'
            },
            success: function (data) {
                if (data == true) {
                    layer.msg("关注成功");
                    $(obj).html("<i class=\"fa fa-check\" style=\"margin-right: 10px\"></i>已关注");
                    $(obj).attr("class", "focus-y");
                }
                else {
                    layer.msg("关注失败，请重试", {icon: 2});
                }
            }
        })

    }
}

function getLength(str) {
    var len = 0;
    for (var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) > 127 || str.charCodeAt(i) == 94) {
            len += 2;
        } else {
            len++;
        }
    }
    return len;
}

function getParam(param) {
    var list = [];
    var url = location.search.substring(1, location.search.length);
    list = url.split('&&');
    for (var i = 0; i < list.length; i++) {
        if (decodeURI(list[i]).match(param) != null) {
            return decodeURI(list[i]).split("=")[1];
        }
    }
    return null
}