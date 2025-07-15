## 專案技術與設計架構

- **主要技術**：Kotlin、Jetpack Compose、Material 3
- **架構模式**：採用 MVI（Model-View-Intent）架構，將 UI 狀態與事件明確分離，提升可維護性與可測試性
- **響應式 UI**：
    - 以 Compose 為基礎，所有畫面皆為響應式設計，UI 狀態由 ViewModel 驅動
- **依賴注入**：使用 Koin 管理依賴，提升模組化與測試便利性
- **UI 統一設計規格**：
    - 所有顏色、字型等皆集中於 `ui/theme` 目錄統一管理，確保設計一致性
    - Typography 與 Color 皆有自定義命名規則，方便維護與擴充
- **深色主題支援**：
    - 依據系統設定自動切換淺色/深色主題，所有元件皆支援深色模式
- **可擴充性**：
    - Data Loader 與 Repository 皆有抽象層，方便日後擴充資料來源
- **測試覆蓋**：
    - 專案包含單元測試，已撰寫測試範圍：
        - CourseViewModel：驗證 UI 狀態、課程資料轉換、標籤與截止文案等顯示邏輯
        - DefaultCourseRepository：驗證課程資料正確取得與 Repository 行為
        - DateUtil：驗證日期格式轉換、天數計算等工具方法
- **建構工具**：採用 Gradle Kotlin DSL（.kts）進行專案建構與依賴管理，語法更現代且易於維護

## Development Environment

**Android Studio** - Android Studio Meerkat | 2024.3.1 Patch 2

**Android SDK** - 21+

**Gradle** - 8.11
