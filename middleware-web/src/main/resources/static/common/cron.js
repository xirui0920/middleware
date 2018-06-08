/**
 * 每周期
 */
function everyTime(dom) {
    var item = $("#cron_" + dom.name);
    item.val("*");
    item.change();
}

/**
 * 不指定
 */
function unAppoint(dom) {
    var name = dom.name;
    var val = "?";
    if (name === "year") {
        val = "";
    }
    var item = $("#cron_" + name);
    item.val(val);
    item.change();
}

/**
 * 周期
 */
function cycle(dom) {
    var name = dom.name;
    var from = $("#" + name + "_from").val();
    var to = $("#" + name + "_to").val();
    var item = $("#cron_" + name);
    item.val(from + "-" + to);
    item.change();
}

/**
 * 从开始
 */
function beginWith(dom) {
    var name = dom.name;
    var start = $("#" + name + "_start").val();
    var every = $("#" + name + "_every").val();
    var item = $("#cron_" + name);
    item.val(start + "/" + every);
    item.change();
}

/**
 * 本月最后一天
 * @param dom
 */
function lastDay(dom) {
    var item = $("#cron_" + dom.name);
    item.val("L");
    item.change();
}

/**
 * 每月第几周的星期几
 * @param dom
 */
function weekOfDay(dom) {
    var name = dom.name;
    var start = $("#week_start").val();
    var every = $("#week_every").val();
    var item = $("#cron_" + name);
    item.val(start + "#" + every);
    item.change();
}

/**
 * 每月最后一周的周几
 * @param dom
 */
function lastWeek(dom) {
    var weekLast = $("#week_last").val();
    var item = $("#cron_" + name);
    item.val(weekLast + "L");
    item.change();
}

/**
 * 每月几号最近的那个工作日
 * @param dom
 */
function workDay(dom) {
    var name = dom.name;
    var dayNear = $("#day_near").val();
    var item = $("#cron_" + name);
    item.val(dayNear + "W");
    item.change();
}

$(function () {
    $(".layui-input").on("onChange", function () {
        $(this).closest("div.layui-input-inline").children().eq(0).click();
    });

    var values = $("input[name^='cron_']");
    var cron = $("#cron_exp");
    values.change(function () {
        var item = [];
        values.each(function () {
            item.push(this.value);
        });
        //获取当前选中tab
        var currentIndex = 0;
        $(".layui-tab-title>li").each(function (i, item) {
            if ($(item).hasClass("layui-this")) {
                currentIndex = i;
                return false;
            }
        });
        //当前选中项之前的如果为*，则都设置成0
        for (var i = currentIndex; i >= 1; i--) {
            if (item[i] !== "*" && item[i - 1] === "*") {
                item[i - 1] = "0";
            }
        }
        //当前选中项之后的如果不为*则都设置成*
        if (item[currentIndex] === "*") {
            for (var i = currentIndex + 1; i < item.length; i++) {
                if (i === 5) {
                    item[i] = "?";
                } else {
                    item[i] = "*";
                }
            }
        }
        cron.val(item.join(" ")).change();
    });

    cron.change(function () {
        initCron();
        $.ajax({
            type: 'POST',
            url: "/face/cron/getExpression",
            contentType: "application/json",
            data: $("#cron_exp").val(),
            async: false,
            dataType: "json",
            success: function (data) {
                if (true === data.success) {
                    var cronExpression = data.list;
                    var htmlStr = "";
                    for (var i = 0; i < cronExpression.length; i++) {
                        htmlStr += "<span class=\"layui-badge layui-bg-green cron-exp\" "
                            + "style=\"margin: 0 3px 6px 3px;\">" + cronExpression[i] + "</span>";
                    }
                    $("#cronExpressions")[0].innerHTML = htmlStr;
                } else {
                    parent.layer.msg(data.message);
                }
            }
        });
    });

    var secondList = $(".second-list").children();
    $("#second_appoint").click(function () {
        if (this.checked) {
            if ($(secondList).filter(":checked").length === 0) {
                $(secondList.eq(0)).attr("checked", true);
            }
            secondList.eq(0).change();
        }
    });

    secondList.change(function () {
        var second_appoint = $("#second_appoint").prop("checked");
        if (second_appoint) {
            var values = [];
            secondList.each(function () {
                if (this.checked) {
                    values.push(this.value);
                }
            });
            var val = "?";
            if (values.length > 0 && values.length < 59) {
                val = values.join(",");
            } else if (values.length === 59) {
                val = "*";
            }
            var item = $("#cron_second");
            item.val(val);
            item.change();
        }
    });

    var minList = $(".minute-list").children();
    $("#minute_appoint").click(function () {
        if (this.checked) {
            if ($(minList).filter(":checked").length === 0) {
                $(minList.eq(0)).attr("checked", true);
            }
            minList.eq(0).change();
        }
    });

    minList.change(function () {
        var min_appoint = $("#minute_appoint").prop("checked");
        if (min_appoint) {
            var values = [];
            minList.each(function () {
                if (this.checked) {
                    values.push(this.value);
                }
            });
            var val = "?";
            if (values.length > 0 && values.length < 59) {
                val = values.join(",");
            } else if (values.length === 59) {
                val = "*";
            }
            var item = $("#cron_minute");
            item.val(val);
            item.change();
        }
    });

    var hourList = $(".hour-list").children();
    $("#hour_appoint").click(function () {
        if (this.checked) {
            if ($(hourList).filter(":checked").length === 0) {
                $(hourList.eq(0)).attr("checked", true);
            }
            hourList.eq(0).change();
        }
    });

    hourList.change(function () {
        var hour_appoint = $("#hour_appoint").prop("checked");
        if (hour_appoint) {
            var values = [];
            hourList.each(function () {
                if (this.checked) {
                    values.push(this.value);
                }
            });
            var val = "?";
            if (values.length > 0 && values.length < 24) {
                val = values.join(",");
            } else if (values.length === 24) {
                val = "*";
            }
            var item = $("#cron_hour");
            item.val(val);
            item.change();
        }
    });

    var dayList = $(".day-list").children();
    $("#day_appoint").click(function () {
        if (this.checked) {
            if ($(dayList).filter(":checked").length === 0) {
                $(dayList.eq(0)).attr("checked", true);
            }
            dayList.eq(0).change();
        }
    });

    dayList.change(function () {
        var day_appoint = $("#day_appoint").prop("checked");
        if (day_appoint) {
            var values = [];
            dayList.each(function () {
                if (this.checked) {
                    values.push(this.value);
                }
            });
            var val = "?";
            if (values.length > 0 && values.length < 31) {
                val = values.join(",");
            } else if (values.length === 31) {
                val = "*";
            }
            var item = $("#cron_day");
            item.val(val);
            item.change();
        }
    });

    var monthList = $(".month-list").children();
    $("#month_appoint").click(function () {
        if (this.checked) {
            if ($(monthList).filter(":checked").length === 0) {
                $(monthList.eq(0)).attr("checked", true);
            }
            monthList.eq(0).change();
        }
    });

    monthList.change(function () {
        var month_appoint = $("#month_appoint").prop("checked");
        if (month_appoint) {
            var values = [];
            monthList.each(function () {
                if (this.checked) {
                    values.push(this.value);
                }
            });
            var val = "?";
            if (values.length > 0 && values.length < 12) {
                val = values.join(",");
            } else if (values.length === 12) {
                val = "*";
            }
            var item = $("#cron_month");
            item.val(val);
            item.change();
        }
    });

    var weekList = $(".week-list").children();
    $("#week_appoint").click(function () {
        if (this.checked) {
            if ($(weekList).filter(":checked").length === 0) {
                $(weekList.eq(0)).attr("checked", true);
            }
            weekList.eq(0).change();
        }
    });

    weekList.change(function () {
        var week_appoint = $("#week_appoint").prop("checked");
        if (week_appoint) {
            var values = [];
            weekList.each(function () {
                if (this.checked) {
                    values.push(this.value);
                }
            });
            var val = "?";
            if (values.length > 0 && values.length < 7) {
                val = values.join(",");
            } else if (values.length === 7) {
                val = "*";
            }
            var item = $("#cron_week");
            item.val(val);
            item.change();
        }
    });
});