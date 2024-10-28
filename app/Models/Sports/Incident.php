<?php

namespace App\Models\Sports;

use App\Models\Library\LibIncidentStatus;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Incident extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'title',
        'desc',
        'lib_incident_status_id',
        'reported_by_id',
    ];
    public function reportedBy(): BelongsTo
    {
        return $this->belongsTo(User::class, 'reported_by_id');
    }
    public function incidentStatus(): BelongsTo
    {
        return $this->belongsTo(LibIncidentStatus::class, 'lib_incident_status_id');
    }
}
