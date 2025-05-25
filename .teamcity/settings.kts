version = "2024.12"

// 1. Сначала объявляем VCS root
vcsRoot {
    id("nir-tc-git") // Уникальный ID
    name("NIR-TC Git") // Произвольное имя (не ссылка на файл)
    url("https://github.com/DaniyarIrkagaliev/nir-tc.git")
   
    branchSpec = "+:refs/heads/master"
    checkoutSubdir = ".teamcity" // Если конфиги в поддиректории
}

// 2. Затем объявляем проект
project {
    // Настройки проекта будут автоматически подхватывать .kts файлы
    // из указанного VCS root
}
