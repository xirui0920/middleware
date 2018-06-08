/**
 * 重写alert
 * @param msg
 * @param callback
 */
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback(index);
        }
    });
};

/**
 * 重写confirm式样框
 * @param msg
 * @param callback
 */
window.confirm = function (msg, title, callback) {
    var okButton = getI18nResource('web.common.operate.ok');
    var cancelButton = getI18nResource('web.common.operate.cancel');
    parent.layer.confirm(msg, {title: title, btn: [okButton, cancelButton]},
        function () {
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
};

/**
 * 获取国际化资源
 * @param key
 */
function getI18nResource(key) {
    var message = '';
    $.ajax({
        type: "post",
        url: '/resource/getI18nResource',
        contentType: "application/json",
        data: key,
        async: false,
        dataType: "json",
        success: function (result) {
            if (true === result.success) {
                message = result.string;
            }
        }
    });
    return message;
}

/**
 * 跳转到添加页面
 * @param url
 * @param width
 * @param height
 */
function add(url, width, height) {
    var addWindow = getI18nResource('web.common.operate.add');
    parent.layer.open({
        type: 2,
        title: addWindow,
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true,
        area: [width + 'px', height + 'px'],
        content: url
    });
}

/**
 * 跳转到修改页面
 * @param url
 * @param objId
 * @param width
 * @param height
 */
function edit(url, objId, width, height) {
    var editWindow = getI18nResource('web.common.operate.edit');
    parent.layer.open({
        type: 2,
        title: editWindow,
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true,
        area: [width + 'px', height + 'px'],
        content: url + "?objId=" + objId
    });
}

/**
 * 详情
 * @param url
 * @param objId
 * @param width
 * @param height
 */
function detail(url, objId, width, height) {
    var detailWindow = getI18nResource('web.common.window.detail');
    parent.layer.open({
        type: 2,
        title: detailWindow,
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true,
        area: [width + 'px', height + 'px'],
        content: url + "?objId=" + objId
    });
}

/**
 * 删除一条数据
 * @param url 请求地址
 * @param objId  选中的id
 */
function deleteOne(url, objId) {
    var deleteOneMessage = getI18nResource('web.common.message.deleteOne');
    var deleteWindow = getI18nResource('web.common.window.delete');
    confirm(deleteOneMessage, deleteWindow, function (index) {
        $.ajax({
            type: "post",
            url: url,
            contentType: "application/json",
            data: objId,
            async: false,
            dataType: "json",
            success: function (result) {
                if (true === result.success) {
                    layer.close(index);
                    $(".search-btn").click();
                    parent.layer.msg(result.message, {icon: 6});
                } else {
                    parent.layer.msg(result.message, {icon: 5});
                }
            }
        });
    });
}

/**
 * 批量删除
 * @param url
 * @param ids
 */
function deleteBatch(url, ids) {
    var deleteBatchMessage = getI18nResource('web.common.message.deleteBatch');
    var deleteWindow = getI18nResource('web.common.window.delete');
    if (ids != null) {
        confirm(deleteBatchMessage, deleteWindow, function (index) {
            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(ids),
                async: false,
                dataType: "json",
                success: function (result) {
                    if (true === result.success) {
                        layer.close(index);
                        $(".search-btn").click();
                        parent.layer.msg(result.message, {icon: 6});
                    } else {
                        parent.layer.msg(result.message, {icon: 5});
                    }
                }
            });
        });
    }
}

/**
 * 保存或修改
 */
layui.use(['form'], function () {
    var form = layui.form;
    form.on('submit(submit)', function (data) {
        var url = $(this).attr("data-url");
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            async: false,
            dataType: "json",
            success: function (result) {
                if (true === result.success) {
                    $t.Refresh();
                    parent.layer.msg(result.message);
                } else {
                    parent.layer.msg(result.message);
                }
            }
        });
        return false;
    });
});

/**
 * 缓存session里存储的用户
 * @returns {*}
 */
function sessionUser() {
    var loginUser = layui.data('loginUser').user;
    if ("" === loginUser || undefined === loginUser) {
        window.location.href = "/page/navigation/login";
        return;
    }
    return loginUser;
}

/**
 * 清理session中的user信息
 */
function dropSessionUser() {
    layui.data('loginUser', {
        key: 'user'
        , remove: true
    });
}

/**
 * 缓存语言
 * @param locale
 */
function sessionLanguage(locale) {
    if ("" === locale || undefined === locale) {
        locale = "zh_CN";
    }
    layui.data('language', {
        key: 'locale'
        , value: locale
    });

    var langArray = $('[name=\'lang\']');
    for (var i = 0; i < langArray.length; i++) {
        var a = langArray[i];
        a.parentElement.className = "";
        if (a.children.length > 0) {
            a.removeChild(a.lastChild);
        }

        if (locale === a.attributes.lang.nodeValue) {
            var icon = document.createElement("i");
            icon.style.marginLeft = "15px";
            a.parentElement.className = "layui-this";
            icon.className = "fas fa-check";
            a.appendChild(icon);
        }
    }
}

/**
 * 获取session中的language信息
 */
function getSessionLanguage() {
    var locale = layui.data('language').locale;
    if ("" === locale || undefined === locale) {
        locale = "zh_CN";
    }
    return locale;
}

/**
 * 清理session中的language信息
 */
function dropSessionLanguage() {
    layui.data('language', {
        key: 'locale'
        , remove: true
    });
}

/**
 * 清理session中的信息
 */
function dropSession() {
    dropSessionUser();
    dropSessionLanguage();
}

window.onload = function () {
    sessionUser();
    sessionLanguage(layui.data('language').locale);
};