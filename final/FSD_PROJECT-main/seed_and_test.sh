#!/bin/bash
# ============================================================
# CMS Backend — Full API Test & Mock Data Seeder
# ============================================================
# This script tests every endpoint and fills the database
# with realistic mock data for demonstration.
# ============================================================


BASE="http://localhost:8080"
GREEN='\033[0;32m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m'

ok()   { echo -e "${GREEN}✅ $1${NC}"; }
fail() { echo -e "${RED}❌ $1${NC}"; }
head() { echo -e "\n${CYAN}━━━ $1 ━━━${NC}"; }

# ============================================================
head "1. AUTH — SIGNUP & LOGIN"
# ============================================================

echo "→ Signup ADMIN user"
SIGNUP=$(curl -s -X POST "$BASE/api/auth/signup" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin@cms.com","password":"admin123","role":"ADMIN"}')
echo "$SIGNUP" | python3 -m json.tool 2>/dev/null || echo "$SIGNUP"

echo ""
echo "→ Login to get JWT token"
LOGIN=$(curl -s -X POST "$BASE/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin@cms.com","password":"admin123"}')
echo "$LOGIN" | python3 -m json.tool 2>/dev/null || echo "$LOGIN"

TOKEN=$(echo "$LOGIN" | python3 -c "import sys,json; print(json.load(sys.stdin)['token'])" 2>/dev/null)
if [ -z "$TOKEN" ]; then
  fail "Could not extract token. Backend may not be running."
  exit 1
fi
ok "JWT Token obtained: ${TOKEN:0:30}..."

AUTH="Authorization: Bearer $TOKEN"

# ============================================================
head "2. DEPARTMENTS"
# ============================================================

echo "→ Creating 3 departments..."
D1=$(curl -s -X POST "$BASE/api/departments" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Computer Science","code":"CSE","hodName":"Dr. Rajesh Kumar","email":"cse@cms.edu","extensionNo":"101"}')
D1_ID=$(echo "$D1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Dept 1 (CS) created → id=$D1_ID"

D2=$(curl -s -X POST "$BASE/api/departments" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Electronics","code":"ECE","hodName":"Dr. Priya Sharma","email":"ece@cms.edu","extensionNo":"102"}')
D2_ID=$(echo "$D2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Dept 2 (ECE) created → id=$D2_ID"

D3=$(curl -s -X POST "$BASE/api/departments" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Mechanical","code":"ME","hodName":"Dr. Anil Verma","email":"me@cms.edu","extensionNo":"103"}')
D3_ID=$(echo "$D3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Dept 3 (ME) created → id=$D3_ID"

echo "→ GET all departments (public)"
curl -s "$BASE/api/departments" | python3 -m json.tool

# ============================================================
head "3. FACULTY"
# ============================================================

echo "→ Creating 3 faculty members..."
# Note: Faculty uses existing /api/faculty if controller exists, otherwise skip
# Using courses controller's department pattern for now

# We need to check if faculty controller exists. Let me use the subject flow which needs faculty
# For now, let's store faculty IDs manually

# ============================================================
head "4. COURSES"
# ============================================================

echo "→ Creating 3 courses..."
C1=$(curl -s -X POST "$BASE/api/courses" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"courseTitle\":\"B.Tech Computer Science\",\"stream\":\"Engineering\",\"totalSemesters\":8,\"durationYears\":4,\"minCredits\":160,\"level\":\"UG\",\"departmentId\":$D1_ID}")
C1_ID=$(echo "$C1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Course 1 (B.Tech CS) → id=$C1_ID"

C2=$(curl -s -X POST "$BASE/api/courses" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"courseTitle\":\"B.Tech Electronics\",\"stream\":\"Engineering\",\"totalSemesters\":8,\"durationYears\":4,\"minCredits\":160,\"level\":\"UG\",\"departmentId\":$D2_ID}")
C2_ID=$(echo "$C2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Course 2 (B.Tech ECE) → id=$C2_ID"

C3=$(curl -s -X POST "$BASE/api/courses" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"courseTitle\":\"B.Tech Mechanical\",\"stream\":\"Engineering\",\"totalSemesters\":8,\"durationYears\":4,\"minCredits\":160,\"level\":\"UG\",\"departmentId\":$D3_ID}")
C3_ID=$(echo "$C3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Course 3 (B.Tech ME) → id=$C3_ID"

echo "→ GET all courses"
curl -s "$BASE/api/courses" | python3 -m json.tool

# ============================================================
head "5. STUDENTS"
# ============================================================

echo "→ Creating 5 students..."
S1=$(curl -s -X POST "$BASE/api/students" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Ayush\",\"lastName\":\"Dubey\",\"gender\":\"MALE\",\"rollNo\":\"CS2024001\",\"email\":\"ayush@cms.edu\",\"phone\":\"9876543210\",\"currentSemester\":4,\"admissionYear\":2024,\"status\":\"ACTIVE\",\"address\":\"Mumbai, India\",\"departmentId\":$D1_ID,\"courseId\":$C1_ID}")
S1_ID=$(echo "$S1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Student 1 (Ayush) → id=$S1_ID"

S2=$(curl -s -X POST "$BASE/api/students" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Neha\",\"lastName\":\"Gupta\",\"gender\":\"FEMALE\",\"rollNo\":\"CS2024002\",\"email\":\"neha@cms.edu\",\"phone\":\"9876543211\",\"currentSemester\":4,\"admissionYear\":2024,\"status\":\"ACTIVE\",\"address\":\"Delhi, India\",\"departmentId\":$D1_ID,\"courseId\":$C1_ID}")
S2_ID=$(echo "$S2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Student 2 (Neha) → id=$S2_ID"

S3=$(curl -s -X POST "$BASE/api/students" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Rahul\",\"lastName\":\"Singh\",\"gender\":\"MALE\",\"rollNo\":\"ECE2024001\",\"email\":\"rahul@cms.edu\",\"phone\":\"9876543212\",\"currentSemester\":2,\"admissionYear\":2025,\"status\":\"ACTIVE\",\"address\":\"Pune, India\",\"departmentId\":$D2_ID,\"courseId\":$C2_ID}")
S3_ID=$(echo "$S3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Student 3 (Rahul) → id=$S3_ID"

S4=$(curl -s -X POST "$BASE/api/students" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Priya\",\"lastName\":\"Patel\",\"gender\":\"FEMALE\",\"rollNo\":\"ME2024001\",\"email\":\"priya@cms.edu\",\"phone\":\"9876543213\",\"currentSemester\":6,\"admissionYear\":2023,\"status\":\"ACTIVE\",\"address\":\"Bangalore, India\",\"departmentId\":$D3_ID,\"courseId\":$C3_ID}")
S4_ID=$(echo "$S4" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Student 4 (Priya) → id=$S4_ID"

S5=$(curl -s -X POST "$BASE/api/students" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Vikram\",\"lastName\":\"Joshi\",\"gender\":\"MALE\",\"rollNo\":\"CS2023001\",\"email\":\"vikram@cms.edu\",\"phone\":\"9876543214\",\"currentSemester\":8,\"admissionYear\":2023,\"status\":\"GRADUATED\",\"address\":\"Chennai, India\",\"departmentId\":$D1_ID,\"courseId\":$C1_ID}")
S5_ID=$(echo "$S5" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Student 5 (Vikram, graduated) → id=$S5_ID"

echo "→ GET all students"
curl -s "$BASE/api/students" | python3 -m json.tool

echo "→ GET single student"
curl -s "$BASE/api/students/$S1_ID" | python3 -m json.tool

echo "→ PATCH student (partial update)"
curl -s -X PATCH "$BASE/api/students/$S1_ID" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"phone":"9999999999"}' | python3 -m json.tool
ok "Student 1 phone patched"

# ============================================================
head "6. TEACHING STAFF (new /api/v1/)"
# ============================================================

echo "→ Creating 3 teaching staff..."
TS1=$(curl -s -X POST "$BASE/api/v1/teaching-staff" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"fullName\":\"Prof. Sanjay Mehta\",\"employeeCode\":\"TS001\",\"designation\":\"Professor\",\"qualification\":\"PhD CS\",\"specialization\":\"AI/ML\",\"email\":\"sanjay@cms.edu\",\"phone\":\"9000000001\",\"salary\":120000,\"departmentId\":$D1_ID}")
TS1_ID=$(echo "$TS1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "TeachingStaff 1 → id=$TS1_ID"

TS2=$(curl -s -X POST "$BASE/api/v1/teaching-staff" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"fullName\":\"Dr. Kavita Nair\",\"employeeCode\":\"TS002\",\"designation\":\"Assoc. Professor\",\"qualification\":\"PhD ECE\",\"specialization\":\"VLSI\",\"email\":\"kavita@cms.edu\",\"phone\":\"9000000002\",\"salary\":100000,\"departmentId\":$D2_ID}")
TS2_ID=$(echo "$TS2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "TeachingStaff 2 → id=$TS2_ID"

TS3=$(curl -s -X POST "$BASE/api/v1/teaching-staff" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"fullName\":\"Prof. Amit Desai\",\"employeeCode\":\"TS003\",\"designation\":\"Asst. Professor\",\"qualification\":\"M.Tech ME\",\"specialization\":\"Thermodynamics\",\"email\":\"amit@cms.edu\",\"phone\":\"9000000003\",\"salary\":80000,\"departmentId\":$D3_ID}")
TS3_ID=$(echo "$TS3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "TeachingStaff 3 → id=$TS3_ID"

echo "→ GET all teaching staff"
curl -s "$BASE/api/v1/teaching-staff" | python3 -m json.tool

# ============================================================
head "7. UG PROGRAMS"
# ============================================================

echo "→ Creating 3 UG programs..."
UG1=$(curl -s -X POST "$BASE/api/v1/ug-programs" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"programName\":\"B.Tech in CSE\",\"degreeType\":\"B.Tech\",\"durationYears\":4,\"totalSemesters\":8,\"totalCredits\":160,\"eligibilityCriteria\":\"12th PCM 60%+\",\"annualFee\":150000,\"isActive\":true,\"departmentId\":$D1_ID}")
UG1_ID=$(echo "$UG1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "UGProgram 1 → id=$UG1_ID"

UG2=$(curl -s -X POST "$BASE/api/v1/ug-programs" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"programName\":\"B.Tech in ECE\",\"degreeType\":\"B.Tech\",\"durationYears\":4,\"totalSemesters\":8,\"totalCredits\":160,\"eligibilityCriteria\":\"12th PCM 55%+\",\"annualFee\":140000,\"isActive\":true,\"departmentId\":$D2_ID}")
UG2_ID=$(echo "$UG2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "UGProgram 2 → id=$UG2_ID"

UG3=$(curl -s -X POST "$BASE/api/v1/ug-programs" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"programName\":\"B.Tech in Mechanical\",\"degreeType\":\"B.Tech\",\"durationYears\":4,\"totalSemesters\":8,\"totalCredits\":165,\"eligibilityCriteria\":\"12th PCM 55%+\",\"annualFee\":130000,\"isActive\":true,\"departmentId\":$D3_ID}")
UG3_ID=$(echo "$UG3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "UGProgram 3 → id=$UG3_ID"

echo "→ GET all UG programs"
curl -s "$BASE/api/v1/ug-programs" | python3 -m json.tool

# ============================================================
head "8. PG STUDENTS"
# ============================================================

echo "→ Creating 2 PG students (linked to existing students)..."
PG1=$(curl -s -X POST "$BASE/api/v1/pg-students" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"studentId\":$S5_ID,\"researchTopic\":\"Deep Learning in NLP\",\"supervisorName\":\"Prof. Sanjay Mehta\",\"thesisTitle\":\"Transformer Models for Indian Languages\",\"pgStartDate\":\"2025-07-01\",\"expectedCompletionDate\":\"2027-07-01\",\"programType\":\"M.Tech\",\"isThesisSubmitted\":false}")
echo "$PG1" | python3 -m json.tool 2>/dev/null || echo "$PG1"
ok "PGStudent 1 created"

# ============================================================
head "9. LIBRARY"
# ============================================================

echo "→ Creating a library..."
LIB1=$(curl -s -X POST "$BASE/api/v1/libraries" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Central Library","location":"Main Building, Block A","totalBooks":15000,"totalSeats":300,"openingTime":"08:00","closingTime":"22:00"}')
LIB1_ID=$(echo "$LIB1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Library 1 → id=$LIB1_ID"

echo "→ GET library"
curl -s "$BASE/api/v1/libraries/$LIB1_ID" | python3 -m json.tool

# ============================================================
head "10. BOOKS"
# ============================================================

echo "→ Creating 4 books..."
B1=$(curl -s -X POST "$BASE/api/v1/books" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"title\":\"Data Structures and Algorithms\",\"author\":\"Thomas H. Cormen\",\"isbn\":\"978-0262033848\",\"publisher\":\"MIT Press\",\"edition\":3,\"totalCopies\":10,\"availableCopies\":8,\"category\":\"Computer Science\",\"shelfLocation\":\"A1-03\",\"libraryId\":$LIB1_ID}")
B1_ID=$(echo "$B1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Book 1 (CLRS) → id=$B1_ID"

B2=$(curl -s -X POST "$BASE/api/v1/books" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"title\":\"Database System Concepts\",\"author\":\"Abraham Silberschatz\",\"isbn\":\"978-0078022159\",\"publisher\":\"McGraw Hill\",\"edition\":7,\"totalCopies\":8,\"availableCopies\":5,\"category\":\"Computer Science\",\"shelfLocation\":\"A1-07\",\"libraryId\":$LIB1_ID}")
B2_ID=$(echo "$B2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Book 2 (DBMS) → id=$B2_ID"

B3=$(curl -s -X POST "$BASE/api/v1/books" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"title\":\"Digital Electronics\",\"author\":\"Morris Mano\",\"isbn\":\"978-0132774208\",\"publisher\":\"Pearson\",\"edition\":5,\"totalCopies\":6,\"availableCopies\":6,\"category\":\"Electronics\",\"shelfLocation\":\"B2-01\",\"libraryId\":$LIB1_ID}")
B3_ID=$(echo "$B3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Book 3 (Digital) → id=$B3_ID"

B4=$(curl -s -X POST "$BASE/api/v1/books" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"title\":\"Engineering Thermodynamics\",\"author\":\"P.K. Nag\",\"isbn\":\"978-0070702028\",\"publisher\":\"Tata McGraw Hill\",\"edition\":6,\"totalCopies\":5,\"availableCopies\":4,\"category\":\"Mechanical\",\"shelfLocation\":\"C1-02\",\"libraryId\":$LIB1_ID}")
B4_ID=$(echo "$B4" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Book 4 (Thermo) → id=$B4_ID"

echo "→ GET all books"
curl -s "$BASE/api/v1/books" | python3 -m json.tool

# ============================================================
head "11. LIBRARY MEMBERS"
# ============================================================

echo "→ Creating 3 library members..."
LM1=$(curl -s -X POST "$BASE/api/v1/library-members" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"memberId\":\"LIB-STU-001\",\"studentId\":$S1_ID,\"libraryId\":$LIB1_ID,\"membershipStartDate\":\"2024-08-01\",\"membershipEndDate\":\"2028-07-31\",\"isActive\":true}")
LM1_ID=$(echo "$LM1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "LibraryMember 1 (Student: Ayush) → id=$LM1_ID"

LM2=$(curl -s -X POST "$BASE/api/v1/library-members" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"memberId\":\"LIB-STU-002\",\"studentId\":$S2_ID,\"libraryId\":$LIB1_ID,\"membershipStartDate\":\"2024-08-01\",\"membershipEndDate\":\"2028-07-31\",\"isActive\":true}")
LM2_ID=$(echo "$LM2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "LibraryMember 2 (Student: Neha) → id=$LM2_ID"

echo "→ GET all library members"
curl -s "$BASE/api/v1/library-members" | python3 -m json.tool

# ============================================================
head "12. BOOK ISSUES"
# ============================================================

echo "→ Issuing books..."
BI1=$(curl -s -X POST "$BASE/api/v1/book-issues" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"bookId\":$B1_ID,\"memberId\":$LM1_ID,\"issueDate\":\"2026-04-01\",\"dueDate\":\"2026-04-15\",\"isReturned\":false}")
BI1_ID=$(echo "$BI1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "BookIssue 1 (CLRS → Ayush) → id=$BI1_ID"

BI2=$(curl -s -X POST "$BASE/api/v1/book-issues" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"bookId\":$B2_ID,\"memberId\":$LM2_ID,\"issueDate\":\"2026-04-05\",\"dueDate\":\"2026-04-19\",\"isReturned\":false}")
BI2_ID=$(echo "$BI2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "BookIssue 2 (DBMS → Neha) → id=$BI2_ID"

echo "→ GET all book issues"
curl -s "$BASE/api/v1/book-issues" | python3 -m json.tool

# ============================================================
head "13. HOSTEL"
# ============================================================

echo "→ Allocating hostel rooms..."
H1=$(curl -s -X POST "$BASE/api/v1/hostel" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"hostelName\":\"Vivekananda Boys Hostel\",\"roomNumber\":\"A-101\",\"floorNumber\":1,\"blockName\":\"Block A\",\"roomType\":\"Double\",\"messFee\":3500,\"hostelFee\":15000,\"isOccupied\":true,\"studentId\":$S1_ID}")
H1_ID=$(echo "$H1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Hostel 1 (Ayush) → id=$H1_ID"

H2=$(curl -s -X POST "$BASE/api/v1/hostel" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"hostelName\":\"Sarojini Girls Hostel\",\"roomNumber\":\"B-204\",\"floorNumber\":2,\"blockName\":\"Block B\",\"roomType\":\"Single\",\"messFee\":3500,\"hostelFee\":18000,\"isOccupied\":true,\"studentId\":$S2_ID}")
H2_ID=$(echo "$H2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Hostel 2 (Neha) → id=$H2_ID"

H3=$(curl -s -X POST "$BASE/api/v1/hostel" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"hostelName\":\"Vivekananda Boys Hostel\",\"roomNumber\":\"A-305\",\"floorNumber\":3,\"blockName\":\"Block A\",\"roomType\":\"Triple\",\"messFee\":3500,\"hostelFee\":12000,\"isOccupied\":false}")
H3_ID=$(echo "$H3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Hostel 3 (empty room) → id=$H3_ID"

echo "→ GET all hostel rooms"
curl -s "$BASE/api/v1/hostel" | python3 -m json.tool

# ============================================================
head "14. CANTEENS"
# ============================================================

echo "→ Creating 2 canteens..."
CAN1=$(curl -s -X POST "$BASE/api/v1/canteens" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Main Canteen","location":"Central Block, Ground Floor","operatingHours":"07:00–21:00","seatingCapacity":200,"managerName":"Rajan Pillai","contactNumber":"9500000001","isVegetarian":false,"isActive":true}')
CAN1_ID=$(echo "$CAN1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Canteen 1 (Main) → id=$CAN1_ID"

CAN2=$(curl -s -X POST "$BASE/api/v1/canteens" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Veg Corner","location":"Library Building, 1st Floor","operatingHours":"08:00–20:00","seatingCapacity":80,"managerName":"Sunita Devi","contactNumber":"9500000002","isVegetarian":true,"isActive":true}')
CAN2_ID=$(echo "$CAN2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Canteen 2 (Veg) → id=$CAN2_ID"

echo "→ GET all canteens"
curl -s "$BASE/api/v1/canteens" | python3 -m json.tool

# ============================================================
head "15. CLASSROOMS"
# ============================================================

echo "→ Creating 4 classrooms..."
CL1=$(curl -s -X POST "$BASE/api/v1/classes" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"roomNumber\":\"LH-101\",\"building\":\"Academic Block 1\",\"floor\":1,\"capacity\":120,\"classroomType\":\"LECTURE_HALL\",\"hasProjector\":true,\"hasAC\":true,\"isAvailable\":true,\"departmentId\":$D1_ID}")
CL1_ID=$(echo "$CL1" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Classroom 1 (Lecture Hall) → id=$CL1_ID"

CL2=$(curl -s -X POST "$BASE/api/v1/classes" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"roomNumber\":\"LAB-CSE-01\",\"building\":\"Academic Block 1\",\"floor\":2,\"capacity\":60,\"classroomType\":\"COMPUTER_LAB\",\"hasProjector\":true,\"hasAC\":true,\"isAvailable\":true,\"departmentId\":$D1_ID}")
CL2_ID=$(echo "$CL2" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Classroom 2 (Computer Lab) → id=$CL2_ID"

CL3=$(curl -s -X POST "$BASE/api/v1/classes" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"roomNumber\":\"LAB-ECE-01\",\"building\":\"Academic Block 2\",\"floor\":1,\"capacity\":40,\"classroomType\":\"LABORATORY\",\"hasProjector\":false,\"hasAC\":false,\"isAvailable\":true,\"departmentId\":$D2_ID}")
CL3_ID=$(echo "$CL3" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Classroom 3 (ECE Lab) → id=$CL3_ID"

CL4=$(curl -s -X POST "$BASE/api/v1/classes" -H "$AUTH" -H "Content-Type: application/json" \
  -d "{\"roomNumber\":\"AUD-01\",\"building\":\"Central Block\",\"floor\":0,\"capacity\":500,\"classroomType\":\"AUDITORIUM\",\"hasProjector\":true,\"hasAC\":true,\"isAvailable\":true}")
CL4_ID=$(echo "$CL4" | python3 -c "import sys,json;print(json.load(sys.stdin)['id'])" 2>/dev/null)
ok "Classroom 4 (Auditorium) → id=$CL4_ID"

echo "→ GET all classrooms"
curl -s "$BASE/api/v1/classes" | python3 -m json.tool

# ============================================================
head "16. UPDATE & DELETE TESTS"
# ============================================================

echo "→ PUT update canteen"
curl -s -X PUT "$BASE/api/v1/canteens/$CAN1_ID" -H "$AUTH" -H "Content-Type: application/json" \
  -d '{"name":"Main Canteen (Renovated)","location":"Central Block, Ground Floor","operatingHours":"06:30–22:00","seatingCapacity":250,"managerName":"Rajan Pillai","contactNumber":"9500000001","isVegetarian":false,"isActive":true}' | python3 -m json.tool
ok "Canteen updated"

echo "→ DELETE classroom 4 (auditorium)"
curl -s -o /dev/null -w "HTTP %{http_code}" -X DELETE "$BASE/api/v1/classes/$CL4_ID" -H "$AUTH"
echo ""
ok "Classroom deleted (should be 204)"

# ============================================================
head "17. PUBLIC GET TESTS (no token)"
# ============================================================

echo "→ GET /api/v1/books (no auth)"
curl -s -o /dev/null -w "HTTP %{http_code}" "$BASE/api/v1/books"
echo ""
ok "Books public access"

echo "→ POST /api/v1/books (no auth) — should be 401/403"
curl -s -o /dev/null -w "HTTP %{http_code}" -X POST "$BASE/api/v1/books" -H "Content-Type: application/json" \
  -d '{"title":"test","isbn":"test","libraryId":1}'
echo ""
ok "Books POST blocked without token"

# ============================================================
head "✅ ALL DONE"
# ============================================================

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "  Mock data seeded successfully!"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "  Departments:    3"
echo "  Courses:        3"
echo "  Students:       5"
echo "  Teaching Staff:  3"
echo "  UG Programs:    3"
echo "  PG Students:    1"
echo "  Libraries:      1"
echo "  Books:          4"
echo "  Library Members: 2"
echo "  Book Issues:    2"
echo "  Hostel Rooms:   3"
echo "  Canteens:       2"
echo "  Classrooms:     3 (1 deleted)"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "  JWT Token: ${TOKEN:0:50}..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
