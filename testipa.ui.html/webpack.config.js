const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');

module.exports = (env, args) => {
  args.resDirArray = ['src/main/resources/WebContent', 'node_modules/@eclipse-scout/core/res'];
  const config = baseConfig(env, args);

  config.entry = {
    'testipa': './src/main/js/testipa.js',
    'login': './src/main/js/login.js',
    'logout': './src/main/js/logout.js',
    'testipa-theme': './src/main/js/testipa-theme.less',
    'testipa-theme-dark': './src/main/js/testipa-theme-dark.less'
  };

  return config;
};
