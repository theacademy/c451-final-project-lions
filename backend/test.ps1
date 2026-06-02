$baseUrl = "http://localhost:8080/job"

# Create a sample job record for companyId 1
$job = [pscustomobject]@{
    greenhouseJobId = 1001
    companyId       = 1
    title           = 'Backend Java Engineer'
    location        = 'Toronto'
    descriptionHtml = '<p>Sample job</p>'
    descriptionText = 'Sample backend job for testing'
    absoluteUrl     = 'https://example.com/job/1001'
    seniorityLevel  = 'Mid'
    skillsCsv       = 'Java,SQL,Python'
}

Write-Host "Creating sample job..."
$createdJob = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType 'application/json' -Body ($job | ConvertTo-Json -Depth 4)
Write-Host "Created job with id:" $createdJob.id
Write-Host ""

# Search using skills list
$searchPayload = [pscustomobject]@{
    companyId = 1
    skills    = @('Java', 'SQL', 'Python')
}

Write-Host "Searching for jobs by skills..."
$searchResult = Invoke-RestMethod -Uri "$baseUrl/search" -Method Post -ContentType 'application/json' -Body ($searchPayload | ConvertTo-Json -Depth 4)
Write-Host "Search result:"
$searchResult | Format-List *
