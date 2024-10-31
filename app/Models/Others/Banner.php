<?php

namespace App\Models\Others;

use App\Models\Sports\Schedule;
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
    ];
    public function schedule(): HasMany 
    {
        return $this->hasMany(Schedule::class, 'banner');
    }
}
