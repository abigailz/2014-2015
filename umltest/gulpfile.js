var gulp = require('gulp');
var sass = require('gulp-sass');
var browserSync = require('browser-sync');
var reload = browserSync.reload;

gulp.task('sass', function () {
    return gulp.src('app/sass/main.scss')
        .pipe(sass())
        .pipe(gulp.dest('app/styles'))
        .pipe(reload({stream: true}));
});

gulp.task('serve', ['sass'], function () {
    browserSync({
        notify: false,
        port: 9000,
        server: {
            baseDir: 'app'
            //baseDir: ["./", 'app']
            //routes: {
            //    '/bower_components': 'bower_components'
            //}
        }
    });
    // watch for changes
    gulp.watch([
        'app/*.html'
    ]).on('change', reload);
    gulp.watch('app/sass/*.scss', ['sass']);
});