// webpack.config.js
module.exports = {
    // メインとなるソースファイル
    entry: './src/index.js',
    // 出力設定
    // この場合はdest/bundle.jsというファイルが生成される
    output: {
        // 出力先のファイル名
        filename: 'bundle.js',
        // 出力先のファイルパス
        path: `${__dirname}/src/main/resources/static/javascript`,
    },
    // 開発サーバの設定
    // devServer: {
    //     // destディレクトリの中身を表示してね、という設定
    //     contentBase: 'javascript',
    // },
};