<?php
namespace app\meditation\model;

use think\Model;
use think\Db;

class SonglistModel extends Model
{

    protected $table = 'songlist';

    public function getsonglistByCategory($category)
    {

        if($category=="全部")
            $data = self::field('songlist_id ,songlist_name,  cover_link')
                    ->select();
        else 
            $data = self::where(['category'=>$category])
                    ->field('songlist_id , songlist_name, cover_link')
                    ->select();
        return $data;
    }

    public function getSongsInSonglist($songlist_id)
    {
        return Db::table('songlist sl')
        ->join('song_songlist ssl','sl.songlist_id=ssl.radio_id','INNER')
        >join('song s','ssl.song_id=s.song_id','INNER')
        ->where(['songlist_id'=>$songlist_id])
        ->field('song_id , song_name , singer_name')
        ->select();
    }

}