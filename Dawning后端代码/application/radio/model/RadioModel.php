<?php
namespace app\radio\model;

use think\Model;
use think\Db;

class RadioModel extends Model
{
    protected $table = 'radio';

    public function getRadios()
    {
        return Db::table('radio')
                ->alias('r')
                ->join('subscribe_radio sr','r.radio_id = sr.radio_id','LEFT')
                ->field(['r.radio_id','r.radio_name','r.cover_link','count(user_id) subscribe_count'])
                ->group('radio_id')
                ->select();
        //group('r.radio_id,r.radio_name,r.cover_link');
    }
}