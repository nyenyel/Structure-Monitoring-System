<?php

namespace App\Models\Others;

use App\Models\Sports\Schedule;
use App\Models\Sports\Team;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Banner extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'title',
        'year',
        'image',
        'is_default',
        'delete',
    ];
    public function schedule(): HasMany 
    {
        return $this->hasMany(Schedule::class, 'banner');
    }
    public function teams(): HasMany 
    {
        return $this->hasMany(Team::class, 'banner');
    }
}
