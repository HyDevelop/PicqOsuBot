var chartHtml = "<div class=\"beatmapset-stats__row beatmapset-stats__row--advanced\" style=\"color: #e0e0e0;\">\n" +
    "    <table class=\"beatmap-stats-table\">\n" +
    "        <tbody>\n" +
    "            <tr>\n" +
    "                <th class=\"beatmap-stats-table__label\">耐力</th>\n" +
    "                <td class=\"beatmap-stats-table__bar\">\n" +
    "                    <div class=\"bar bar--beatmap-stats%{1star}\">\n" +
    "                        <div class=\"bar__fill\" style=\"width: %{1p}%;\"></div>\n" +
    "                    </div>\n" +
    "                </td>\n" +
    "                <td class=\"beatmap-stats-table__value\">%{1}</td>\n" +
    "            </tr>\n" +
    "            <tr>\n" +
    "                <th class=\"beatmap-stats-table__label\">打串</th>\n" +
    "                <td class=\"beatmap-stats-table__bar\">\n" +
    "                    <div class=\"bar bar--beatmap-stats%{2star}\">\n" +
    "                        <div class=\"bar__fill\" style=\"width: %{2p}%;\"></div>\n" +
    "                    </div>\n" +
    "                </td>\n" +
    "                <td class=\"beatmap-stats-table__value\">%{2}</td>\n" +
    "            </tr>\n" +
    "            <tr>\n" +
    "                <th class=\"beatmap-stats-table__label\">移动</th>\n" +
    "                <td class=\"beatmap-stats-table__bar\">\n" +
    "                    <div class=\"bar bar--beatmap-stats%{3star}\">\n" +
    "                        <div class=\"bar__fill\" style=\"width: %{3p}%;\"></div>\n" +
    "                    </div>\n" +
    "                </td>\n" +
    "                <td class=\"beatmap-stats-table__value\">%{3}</td>\n" +
    "            </tr>\n" +
    "            <tr>\n" +
    "                <th class=\"beatmap-stats-table__label\">精准</th>\n" +
    "                <td class=\"beatmap-stats-table__bar\">\n" +
    "                    <div class=\"bar bar--beatmap-stats%{4star}\">\n" +
    "                        <div class=\"bar__fill\" style=\"width: %{4p}%;\"></div>\n" +
    "                    </div>\n" +
    "                </td>\n" +
    "                <td class=\"beatmap-stats-table__value\">%{4}</td>\n" +
    "            </tr>\n" +
    "            <tr>\n" +
    "                <th class=\"beatmap-stats-table__label\">瞄准</th>\n" +
    "                <td class=\"beatmap-stats-table__bar\">\n" +
    "                    <div class=\"bar bar--beatmap-stats%{5star}\">\n" +
    "                        <div class=\"bar__fill\" style=\"width: %{5p}%;\"></div>\n" +
    "                    </div>\n" +
    "                </td>\n" +
    "                <td class=\"beatmap-stats-table__value\">%{5}</td>\n" +
    "            </tr>\n" +
    "            <tr>\n" +
    "                <th class=\"beatmap-stats-table__label\">反应</th>\n" +
    "                <td class=\"beatmap-stats-table__bar\">\n" +
    "                    <div class=\"bar bar--beatmap-stats%{6star}\">\n" +
    "                        <div class=\"bar__fill\" style=\"width: %{6p}%;\"></div>\n" +
    "                    </div>\n" +
    "                </td>\n" +
    "                <td class=\"beatmap-stats-table__value\">%{6}</td>\n" +
    "            </tr>\n" +
    "        </tbody>\n" +
    "    </table>\n" +
    "</div>\t";

// 要移除的类
var classToRemove =
[
    "profile-badges",
    "profile-previous-usernames",
    "supporter-icon",
    "profile-info__title",
    "profile-stats__row",
    "profile-header-extra__rank-country"
];

$(".profile-stats__row").not(".profile-stats__row--compact").remove();

// 放表和Title
$(chartHtml).insertAfter(".profile-stats__row");
var statsTable = $(".beatmap-stats-table");
$(".profile-header-extra__rank-box").insertBefore(statsTable);
statsTable.prev().insertAfter(statsTable);
$(".profile-header-extra__rank-global").html("<div class=\"profile-header-extra__rank-global\">osu!Skills 能力评分</div>");

// 移除要移除的类
classToRemove.forEach(function (className)
{
    $("." + className).remove();
});

// 获取HTML Body
var jQueryBody = $("body");
var bodyHtml = jQueryBody.html();

// 替换Body HTML
jQueryBody.html(bodyHtml);

// 获取是否Supporter, 如果是, 添加supporter标注
var isSupporter = /"is_supporter":true/g.test(bodyHtml);
if (isSupporter)
{
    $("<span class=\"profile-info__title\">osu! Supporter</span>").insertAfter(".profile-info__name")
}