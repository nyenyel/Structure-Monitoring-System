<?php

namespace App\Models;

use App\Models\Others\Banner;
use App\Models\Sports\College;
use App\Models\Sports\Sports;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Checklist extends Model
{
    use HasFactory;

    protected $guarded = ['id'];
    protected $fillable = [
        'banner_id',
        'college_id',
        'sport_id',
    ];

    public function banner(): BelongsTo {
        return $this->belongsTo(Banner::class , 'banner_id');
    }
    public function college(): BelongsTo {
        return $this->belongsTo(College::class , 'college_id');
    }
    public function sport(): BelongsTo {
        return $this->belongsTo(Sports::class , 'sport_id');
    }
}
