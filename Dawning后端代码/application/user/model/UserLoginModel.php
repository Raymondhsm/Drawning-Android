<?php
namespace app\user\model;

use think\Model;

class UserLoginModel extends Model
{
    protected $table='user_login';

    public function getUserIdByToken($token){
        $data = $this->where('token',$token)->column('user_id');
        return $data;
    }
}