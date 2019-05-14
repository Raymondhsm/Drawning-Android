<?php
namespace app\meditation\controller;

use app\common\FileRepository;

class MeditationController
{
    

    public function getSonglistByCategory()
    {   
        $category = \input('category');
        return model('MeditationLogic','logic')->getSonglistByCategory($category);
    }
    
    public function getSongsInSonglist($songlist_id)
    {
        return model('MeditationLogic','logic')->getSongsInSonglist($songlist_id);
    }

    
}