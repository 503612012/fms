var accounting = {

    /**
     *格式化数字为货币
     *
     * number：      待格式化的数字
     * symbol：      货币符号
     * precision：   小数位数
     */
    formatMoney: function(number, symbol, precision) {
        if (number == 0) {
            return symbol + number;
        }
        var number = number.toFixed(precision);
        var decimal = '';//小数部分
        var index = number.lastIndexOf('.');
        if (index != -1) {
            decimal = number.substring(index + 1);
            number = number.substring(0, index);
        }
        var counter = 0;
        var result = '';
        for (var i = number.length - 1; i >= 0; i--) {
            counter++;
            result = number.charAt(i) + result;
            if (counter % 4 == 0 && i != 0) {
                result = ',' + result;
            }
        }
        if (decimal != '')
            result += ("." + decimal);
        result = symbol + result;
        return result;
    }

};