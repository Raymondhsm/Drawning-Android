<?php
namespace app\meditation\logic;

use think\Model;
use app\meditation\model\SongModel;
use app\meditation\model\SonglistModel;

class MeditationLogic extends Model
{
    //冥想歌单分类
    public function getSonglistByCategory($category)
    {   
        return \think\Loader::model('SonglistModel','model')->getSonglistByCategory($category);
    }

    
    public function getSongsInSonglist($songlist_id)
    {
        return \think\Loader::model('SonglistModel','model')->getSongsInSonglist($songlist_id);
    }

    
}