<?php
namespace app\radio\model;

use think\Model;
use think\Db;

class BannerModel extends Model
{
    protected $table = 'banner';

    public function getLatestBanners()
    {
        return self::where('status',1)->column('banner_link');
    }
}