<?php
namespace app\diary\model;

use think\Model;
use think\Db;
use app\user\model\UserLoginModel;

class DiaryModel extends Model
{
    protected $table = 'diary';

    public function getDiary($token)
    {
        $user=new UserLoginModel();
        $userID=$user->getUserIdByToken($token)[0];
        return $this -> where("user_id=$userID")->select();
       
    }
}