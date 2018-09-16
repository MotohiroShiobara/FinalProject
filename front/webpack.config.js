const path = require('path');

module.exports = {
    // メインとなるソースファイル
    entry: './src/index.js',
    // 出力設定
    // この場合はdest/bundle.jsというファイルが生成される
    output: {
        // 出力先のファイル名
        filename: 'bundle.js',
        // 出力先のファイルパス
        path: `${__dirname}/../src/main/resources/static/javascript`,
    },
    mode: 'development',
    module: {
        rules: [
            // {
            //     test: /\.vue$/,
            //     loader: 'vue-loader',
            // },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader'
                }
            },
            // {
            //     test: /\.(css|sass|scss)$/,
            //     loader: 'sass-loader',
            // },
        ]
    },
    devServer: {
        contentBase: 'public',
    },
};