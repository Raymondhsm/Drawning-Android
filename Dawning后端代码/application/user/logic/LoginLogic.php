<?php
namespace app\user\logic;

use think\Request;
use app\user\model\UserModel;
use app\user\model\UserLoginModel;

class LoginLogic
{
    public function Login($account,$psd){
        $user = new UserLoginModel();
        $data = $user->where("identifier=$account")->find();
        if($data == null) return -1 ;
        if( $data['credential'] == $psd){

            $result = $user->join('user','user.user_id=user_login.user_id');
            $user_id = $data['user_id'];
            $userData = $result->where("user.user_id=$user_id")->find();

            return array(
                'user_id' => $userData['user_id'],
                'user_name' => $userData['user_name'],
                'avatar_link' => $userData['avatar_link'],
                'token' => $userData['token']
            );
        }
        else return -2;
    }

    public function Register($account,$psd,$name,$sex,$age,$avatar){
        $user = new UserModel;
        $user -> user_name = $name;
        $user -> sex = $sex;
        $user -> age = $age;
        $user -> avatar_link = null;
        $user -> save();

        $user_id = $user -> user_id;
        $token = $account.$psd;

        $login = new UserLoginModel;
        $login -> user_id = $user_id;
        $login -> identifier = $account;
        $login -> credential = $psd;
        $login -> token = $token;
        $login -> save();

        return $this->Login($account,$psd);
    }
}