ALTER TABLE academies ADD FULLTEXT INDEX ft_index (academy_name) WITH PARSER ngram;

INSERT INTO academies (id, academy_name, contact, shuttle, area_of_expertise, full_address, latitude, longitude, point)
VALUES
    (1, '유원우 코딩학원', '000-0000-0000', 'AVAILABLE', '예능(대)', '경기도 성남시 중원구 망포동', 37.45031889396123, 127.14792475623862, POINT(127.14792475623862, 37.45031889396123)),
    (2, '박세영 코딩학원', '000-0000-0000', 'AVAILABLE', '예능(대)', '경기도 성남시 중원구 망포동', 37.44930055207697, 127.12897765110533, POINT(127.12897765110533, 37.44930055207697)),
    (3, '김별 코딩학원', '000-0000-0000', 'AVAILABLE', '예능(대)', '경기도 성남시 중원구 망포동', 37.45368726664004, 127.14137392998435, POINT(127.14137392998435, 37.45368726664004)),
    (4, '김희석보스 코딩학원', '000-0000-0000', 'AVAILABLE', '예능(대)', '경기도 성남시 중원구 망포동', 37.447142768643374, 127.14984342954963, POINT(127.14984342954963, 37.447142768643374)),
    (5, '김유진 코딩학원', '000-0000-0000', 'AVAILABLE', '예능(대)', '경기도 성남시 중원구 망포동', 37.44760417164698, 127.1496783655938, POINT(127.1496783655938, 37.44760417164698));

INSERT INTO lessons (academies_id, subject, curriculum, capacity, duration, total_fee)
VALUES
    (1, 'DB에 대해서', '인덱스란 뭘까', 20, '1개월', 100000),
    (2, '자바 프로그래밍', '객체지향 프로그래밍 기초', 25, '2개월', 120000),
    (3, '웹 개발 기초', 'HTML, CSS, JavaScript', 30, '3개월', 150000),
    (4, '데이터 구조와 알고리즘', '자료구조와 알고리즘 학습', 15, '1개월', 90000),
    (5, '모바일 앱 개발', 'Android 및 iOS 개발 기초', 18, '2개월', 110000);

INSERT INTO review_counts (kindnessCount, goodFacilityCount, cheapFeeCount, goodManagementCount, lovelyTeachingCount, reviewersCount, academies_id)
VALUES (0, 0, 0, 0, 0, 0, 1),
       (0, 0, 0, 0, 0, 0, 2),
       (0, 0, 0, 0, 0, 0, 3),
       (0, 0, 0, 0, 0, 0, 4),
       (0, 0, 0, 0, 0, 0, 5);
