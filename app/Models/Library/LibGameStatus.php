<?php

namespace App\Models\Library;

use App\Models\Sports\Schedule;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class LibGameStatus extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'desc'
    ];
    public function schedule(): HasMany 
    {
        return $this->hasMany(Schedule::class, 'lib_game_statuses_id');
    }
}
