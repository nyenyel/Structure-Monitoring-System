<?php

namespace App\Models\Sports;

use App\Models\Library\LibPosition;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Sports extends Model
{
    use HasFactory;

    protected $guarded = ['id'];
    protected $fillable = [
        'title',
        'venue',
        'logo',
        'delete'

    ];

    public function schedule():HasMany
    {
        return $this->hasMany(Schedule::class,'sports_id')
                    ->whereHas('banner', function ($query) {
                        $query->where('is_default', true);
                    });
    }
    public function position(): HasMany
    {
        return $this->hasMany(LibPosition::class, 'sports_id')->where('delete', false);
    }
    public function team() : HasMany
    {
        return $this->hasMany(Team::class, 'sports_id');
    }
}
