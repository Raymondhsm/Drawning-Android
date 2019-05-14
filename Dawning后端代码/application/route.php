<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2018 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

use think\Route;
Route::rule('meditationReport','user/user/meditationReport');
Route::rule('moodReport','user/user/moodReport');
Route::rule('programMix','user/user/getProgramMix');
Route::rule('myRadio','user/user/getMyRadio');
Route::rule('myArticle','user/user/getMyArticle');
Route::rule('favoriteArticle','user/user/getFavoriteArticle');
Route::rule('login','user/user/Login');
Route::rule('register',"user/user/Register");

return [
    '__pattern__' => [
        'name' => '\w+',
    ],
    '[hello]'     => [
        ':id'   => ['index/hello', ['method' => 'get'], ['id' => '\d+']],
        ':name' => ['index/hello', ['method' => 'post']],
    ],

];
