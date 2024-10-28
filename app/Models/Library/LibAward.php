<?php

namespace App\Models\Library;

use App\Models\Sports\Team;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class LibAward extends Model
{
    use HasFactory;
    
    protected $guarded = ['id'];
    protected $fillable = [
        'desc'
    ];
    public function team(): HasMany
    {
        return $this->hasMany(Team::class, 'lib_award_id');
    }

}
