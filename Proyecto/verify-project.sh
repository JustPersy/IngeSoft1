#!/bin/bash

# Scalia Project Verification Script
# Verifica que todos los componentes del proyecto estén en su lugar

echo "🎵 SCALIA - PROJECT VERIFICATION"
echo "=================================="

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Counters
SUCCESS=0
TOTAL=0

check_file() {
    local file="$1"
    local description="$2"
    TOTAL=$((TOTAL + 1))
    
    if [ -f "$file" ]; then
        echo -e "✅ ${GREEN}FOUND${NC}: $description"
        SUCCESS=$((SUCCESS + 1))
    else
        echo -e "❌ ${RED}MISSING${NC}: $description ($file)"
    fi
}

check_directory() {
    local dir="$1"
    local description="$2"
    TOTAL=$((TOTAL + 1))
    
    if [ -d "$dir" ]; then
        echo -e "✅ ${GREEN}FOUND${NC}: $description"
        SUCCESS=$((SUCCESS + 1))
    else
        echo -e "❌ ${RED}MISSING${NC}: $description ($dir)"
    fi
}

echo "📁 Checking Project Structure..."

# Main structure
check_file "pom.xml" "Maven configuration"
check_file "README_FINAL.md" "Final documentation"
check_file "run-app.sh" "Execution script"

# Source directories
check_directory "src/main/java/com/scalia" "Main source directory"
check_directory "src/test/java/com/scalia" "Test source directory"
check_directory "src/main/resources" "Resources directory"

# Main classes
check_file "src/main/java/com/scalia/Main.java" "Main application class"

# Controllers
echo ""
echo "🎮 Checking Controllers..."
check_file "src/main/java/com/scalia/controllers/MainController.java" "Main controller"
check_file "src/main/java/com/scalia/controllers/LoginController.java" "Login controller"
check_file "src/main/java/com/scalia/controllers/RegisterController.java" "Register controller"
check_file "src/main/java/com/scalia/controllers/SplashController.java" "Splash controller"
check_file "src/main/java/com/scalia/controllers/InstrumentLibraryController.java" "Instrument library controller"
check_file "src/main/java/com/scalia/controllers/TheoryController.java" "Theory controller"
check_file "src/main/java/com/scalia/controllers/ChordController.java" "Chord controller"
check_file "src/main/java/com/scalia/controllers/TunerController.java" "Tuner controller"
check_file "src/main/java/com/scalia/controllers/TuningLibraryController.java" "Tuning library controller"

# Models
echo ""
echo "📋 Checking Models..."
check_file "src/main/java/com/scalia/models/User.java" "User model"
check_file "src/main/java/com/scalia/models/Instrument.java" "Instrument model"
check_file "src/main/java/com/scalia/models/InstrumentCategory.java" "Instrument category model"
check_file "src/main/java/com/scalia/models/Chord.java" "Chord model"
check_file "src/main/java/com/scalia/models/TuningPreset.java" "Tuning preset model"
check_file "src/main/java/com/scalia/models/TheoryContent.java" "Theory content model"

# DAOs
echo ""
echo "🗄️ Checking Data Access Objects..."
check_file "src/main/java/com/scalia/dao/UserDAO.java" "User DAO"
check_file "src/main/java/com/scalia/dao/InstrumentDAO.java" "Instrument DAO"
check_file "src/main/java/com/scalia/dao/InstrumentCategoryDAO.java" "Instrument category DAO"
check_file "src/main/java/com/scalia/dao/ChordDAO.java" "Chord DAO"
check_file "src/main/java/com/scalia/dao/TuningPresetDAO.java" "Tuning preset DAO"
check_file "src/main/java/com/scalia/dao/TheoryContentDAO.java" "Theory content DAO"

# Utilities
echo ""
echo "🛠️ Checking Utilities..."
check_file "src/main/java/com/scalia/utils/DatabaseConnection.java" "Database connection utility"
check_file "src/main/java/com/scalia/utils/ValidationUtils.java" "Validation utility"

# Services
echo ""
echo "⚙️ Checking Services..."
check_file "src/main/java/com/scalia/services/InstrumentService.java" "Instrument service"

# FXML Views
echo ""
echo "🎨 Checking FXML Views..."
check_file "src/main/resources/fxml/SplashView.fxml" "Splash view"
check_file "src/main/resources/fxml/LoginView.fxml" "Login view"
check_file "src/main/resources/fxml/RegisterView.fxml" "Register view"
check_file "src/main/resources/fxml/MainView.fxml" "Main view"
check_file "src/main/resources/fxml/InstrumentLibraryView.fxml" "Instrument library view"
check_file "src/main/resources/fxml/TheoryView.fxml" "Theory view"
check_file "src/main/resources/fxml/ChordView.fxml" "Chord view"
check_file "src/main/resources/fxml/TunerView.fxml" "Tuner view"
check_file "src/main/resources/fxml/TuningLibraryView.fxml" "Tuning library view"

# Styles and Assets
echo ""
echo "🎨 Checking Styles and Assets..."
check_file "src/main/resources/css/styles.css" "Main stylesheet"
check_directory "src/main/resources/assets" "Assets directory"
check_directory "src/main/resources/fonts" "Fonts directory"

# Database
echo ""
echo "🗄️ Checking Database..."
check_file "database/scalia_db.sql" "Database schema and data"

# Tests
echo ""
echo "🧪 Checking Tests..."
check_file "src/test/java/com/scalia/utils/ValidationUtilsTest.java" "Validation utils test"
check_file "src/test/java/com/scalia/models/UserTest.java" "User model test"
check_file "src/test/java/com/scalia/models/InstrumentTest.java" "Instrument model test"
check_file "src/test/java/com/scalia/dao/UserDAOTest.java" "User DAO test"
check_file "src/test/java/com/scalia/dao/InstrumentDAOTest.java" "Instrument DAO test"

# Summary
echo ""
echo "📊 VERIFICATION SUMMARY"
echo "======================="
echo -e "✅ Success: ${GREEN}$SUCCESS${NC}"
echo -e "❌ Missing: ${RED}$((TOTAL - SUCCESS))${NC}"
echo -e "📊 Total: ${YELLOW}$TOTAL${NC}"

if [ $SUCCESS -eq $TOTAL ]; then
    echo ""
    echo -e "🎉 ${GREEN}PROJECT IS COMPLETE AND READY FOR DELIVERY!${NC}"
    echo ""
    echo "🚀 To run the application:"
    echo "   ./run-app.sh"
    echo ""
    echo "📖 For detailed information, see README_FINAL.md"
else
    echo ""
    echo -e "⚠️ ${YELLOW}Project has missing components. Please review the missing files above.${NC}"
fi

echo ""
echo "🎵 Scalia verification complete."
